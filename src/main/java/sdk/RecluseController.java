package sdk;

import sdk.Bean.*;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@RestController
public class RecluseController  {
    BigInteger P = new BigInteger("27449016760001930830125617668247859359745430986704432309085929677995807338305678419885652753794242496407976182871494825520552812933077776662415387179356727792234451654055658177210561689664280863983972105655690337291450879107894692596167210983185326590416340066192826004270803522574046051860102165172244586056707834948333241005980694712476760216030160395462421297530733396614124258707965156976358959843617034441619164457098759725517618250918992025137481460788874501395889763568442978210065547845149324569983455235117868998436516548293413794803510612425480720620581614363153446128915785524341773999191504845709308817083");
    BigInteger g = new BigInteger("3");
    BigInteger basic_client_id = new BigInteger("4041426645612503873277003983038666454650576160323940566454715559491867392402873673364732162751113004393181711842780439723342928416544245135219907888132303791336897060309510567070320915877118127240231546620747676501728434631842250994916042752513101232748266554554547119384050998960921835012553915607248940490391665119480158206361899154104856814829214469435169307762601410054507581307143712827197102729172255582341259019582926017839184640439004418536133687852521072743550448484993159734928673214729233959910822647778787154652908525528171087279481713294114765983651303768616715943475653169406968995502342502428362211706");
    @RequestMapping(value = "/uploadPK", method = RequestMethod.POST)
    public String uploadPK(@RequestBody String body, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println("/uploadPK");
        Gson gson = new Gson();
        DHKey dhKey_client = gson.fromJson(body, DHKey.class);
        String ID = dhKey_client.getID();
        String pk_client = dhKey_client.getPk_client();
        String result = dhKey_client.getResult();
        DHKey dhKey = (DHKey) DHKeyManager.getByName(ID);
        String sk_server = dhKey.getSk_server();
        BigInteger pk = new BigInteger(pk_client);
        BigInteger sk = new BigInteger(sk_server);
        BigInteger tempResult = pk.modPow(sk, P);
        if(tempResult.mod(new BigInteger("2")).compareTo(BigInteger.ONE) != 0){
            tempResult = tempResult.add(BigInteger.ONE);
        }
        String result_server = tempResult.toString();

        response.setHeader("Access-Control-Allow-Origin", "*");
        if(result.equals(result_server)){
            dhKey.setResult(result);
            dhKey.setPk_client(pk_client);
            BigInteger client_id = basic_client_id.modPow(new BigInteger(result), P);
            dhKey.setClient_id(client_id.toString());
            return "{\"result\":\"ok\", \"client_id\": \""+client_id.toString()+"\"}";
        }else {
            return "{\"result\":\"error\"}";
        }
    }

    @RequestMapping(value = "/register_finished", method = RequestMethod.POST)
    public ModelAndView register_finished(@RequestBody String body, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println("/register_finished");
        Gson gson = new Gson();
        RegistrationResult registrationResult = gson.fromJson(body, RegistrationResult.class);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(registrationResult.isResultOK()){
            DHKey dhKey = (DHKey)DHKeyManager.getByName(registrationResult.getID());
            if(registrationResult.getClient_id().equals(dhKey.getClient_id())){
                return new ModelAndView("redirect:http://10.10.81.42:8080/openid-connect-server-webapp/authorize?client_id=" + registrationResult.getClient_id() + "&redirect_uri=" + registrationResult.getRedirect_uri() + "&response_type=token&scope=openid%20email");
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    @RequestMapping(value = "/end", method = RequestMethod.GET)
    public String end( HttpServletRequest request, HttpSession session, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
//        end = new Date().getTime();
//        count++;
//
//
//        System.out.println("Negotiation: " + (point1 + point3 - point2 - start));
//        System.out.println("NegotiationTotal : : " + (point3 - start));



//
//        long negotiation = point3 - start;
//        long registration = point5 - point3;
//        long tokenObtaining = point7 - point5;
//        long total1 = negotiation + registration + tokenObtaining;
//        totalNegotiation += negotiation;
//        totalRegistration += registration;
//        totalTokenObtaining += tokenObtaining;
//        long totalTotal = totalNegotiation + totalRegistration + totalTokenObtaining;
//        System.out.println(count);
//        System.out.println("negotiation: " + totalNegotiation/count + "ms");
//        System.out.println("registration: " + totalRegistration/count + "ms");
//        System.out.println("tokenObtaining: " + totalTokenObtaining/count + "ms");
//        System.out.println("total: " + totalTotal/count + "ms");
        return "end";
    }



}
