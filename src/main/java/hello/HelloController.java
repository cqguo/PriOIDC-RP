package hello;

import Bean.*;
import Tools.Util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@RestController
public class HelloController {

    long start, end, point1, point2, point3, point4, point5, point6, point7;
    int count = 0;
    long totalNegotiation = 0;
    long totalRegistration = 0;
    long totalTokenObtaining = 0;
    String e = "AQAB";
    String n = "qt6yOiI_wCoCVlGO0MySsez0VkSqhPvDl3rfabOslx35mYEO-n4ABfIT5Gn2zN-CeIcOZ5ugAXvIIRWv5H55-tzjFazi5IKkOIMCiz5__MtsdxKCqGlZu2zt-BLpqTOAPiflNPpM3RUAlxKAhnYEqNha6-allPnFQupnW_eTYoyuzuedT7dSp90ry0ZcQDimntXWeaSbrYKCj9Rr9W1jn2uTowUuXaScKXTCjAmJVnsD75JNzQfa8DweklTyWQF-Y5Ky039I0VIu-0CIGhXY48GAFe2EFb8VpNhf07DP63p138RWQ1d3KPEM9mYJVpQC68j3wzDQYSljpLf9by7TGw";
    String RPCert = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3N1ZXIiOiJodHRwOi8vMTAuMTAuODEuNDI6ODA4MCIsImJhc2ljX2NsaWVudF9pZCI6IjQwNDE0MjY2NDU2MTI1MDM4NzMyNzcwMDM5ODMwMzg2NjY0NTQ2NTA1NzYxNjAzMjM5NDA1NjY0NTQ3MTU1NTk0OTE4NjczOTI0MDI4NzM2NzMzNjQ3MzIxNjI3NTExMTMwMDQzOTMxODE3MTE4NDI3ODA0Mzk3MjMzNDI5Mjg0MTY1NDQyNDUxMzUyMTk5MDc4ODgxMzIzMDM3OTEzMzY4OTcwNjAzMDk1MTA1NjcwNzAzMjA5MTU4NzcxMTgxMjcyNDAyMzE1NDY2MjA3NDc2NzY1MDE3Mjg0MzQ2MzE4NDIyNTA5OTQ5MTYwNDI3NTI1MTMxMDEyMzI3NDgyNjY1NTQ1NTQ1NDcxMTkzODQwNTA5OTg5NjA5MjE4MzUwMTI1NTM5MTU2MDcyNDg5NDA0OTAzOTE2NjUxMTk0ODAxNTgyMDYzNjE4OTkxNTQxMDQ4NTY4MTQ4MjkyMTQ0Njk0MzUxNjkzMDc3NjI2MDE0MTAwNTQ1MDc1ODEzMDcxNDM3MTI4MjcxOTcxMDI3MjkxNzIyNTU1ODIzNDEyNTkwMTk1ODI5MjYwMTc4MzkxODQ2NDA0MzkwMDQ0MTg1MzYxMzM2ODc4NTI1MjEwNzI3NDM1NTA0NDg0ODQ5OTMxNTk3MzQ5Mjg2NzMyMTQ3MjkyMzM5NTk5MTA4MjI2NDc3Nzg3ODcxNTQ2NTI5MDg1MjU1MjgxNzEwODcyNzk0ODE3MTMyOTQxMTQ3NjU5ODM2NTEzMDM3Njg2MTY3MTU5NDM0NzU2NTMxNjk0MDY5Njg5OTU1MDIzNDI1MDI0MjgzNjIyMTE3MDYiLCJyZWRpcmVjdF91cmkiOiJodHRwOi8vMTAuMTAuODEuODk6ODA4MCIsImNsaWVudF9uYW1lIjoicHJvdG90eXBlIiwiaWF0IjoxNTE2MjM5MDIyfQ.i59VxZaZRpxhsw6f/jr2Ko+crnf/qYmUeAvi1SKxe9dARmmKwSyGeLc7KsAAofKfIkoP9sSdxR/mvXE5qCM/XSoDHoZjn49WxYprrCWlI7CbYnW/FtasbpEpnI6M0AW7vcoFAEty2ePJbKChJw5/AxjBTdN6EVxg2SJOuzm2RkeLJSC5jNm7qY6cFmWE/ZMzlO5vZ+iVtPcoU079Fc5f9v8keIdBwyEZkwv5d/KivCZO223zzPKcE2wXoTlqefqrHbvSkvVKHJe0xeYWdeYcTM6HnHP8IW2covVYL6NYkVq6jwaPGkLO9kimsuBSVgEbKnwN8EexqLc4HGWojTlAOA==";

//    BigInteger P = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AACAA68FFFFFFFFFFFFFFFF", 16);
//    BigInteger g = new BigInteger("19299511126646292735677292065102783378778561114499641085286923120222713237601305948723274636946434828439258268451017100017289362609809530943953925122724233079625518307164581865346039279958415260851483914979882607249628154657564772465176937924043016651149702794189608748225948183639860129062463800074390249087334605293869748396724146880710440740089853821262200757261880275288528764000814009047632654386817621518213519647015345016546802807219266477048059044879836930479745054159401430016737064411017654823699890141551946139887889860734567892357377796679842840391597803008049834321595373016445579567494805998016197754762");
//    BigInteger basic_client_id = new BigInteger("8065056338595004263849558545591699357501065012723860989763342176682567102932390430607315480515001800690887060328525391846354333803955485169860120806875258552562080752882074644785913518058334334282163885483071758028240486924220171429567638500030042375763640845187004310839106836642781270747534151423175903397454319628249069428081234427713691546345156135960063045414115563137460345052934272095629260136037533852803447592588331323399380073691952023175610595253030419139157717976635921580926950333646955905077471439685898346180800863290723154797865266692278939276246516201606269665870512301699612237379517785321423394949");
    BigInteger P = new BigInteger("27449016760001930830125617668247859359745430986704432309085929677995807338305678419885652753794242496407976182871494825520552812933077776662415387179356727792234451654055658177210561689664280863983972105655690337291450879107894692596167210983185326590416340066192826004270803522574046051860102165172244586056707834948333241005980694712476760216030160395462421297530733396614124258707965156976358959843617034441619164457098759725517618250918992025137481460788874501395889763568442978210065547845149324569983455235117868998436516548293413794803510612425480720620581614363153446128915785524341773999191504845709308817083");
    BigInteger g = new BigInteger("3");
    BigInteger basic_client_id = new BigInteger("4041426645612503873277003983038666454650576160323940566454715559491867392402873673364732162751113004393181711842780439723342928416544245135219907888132303791336897060309510567070320915877118127240231546620747676501728434631842250994916042752513101232748266554554547119384050998960921835012553915607248940490391665119480158206361899154104856814829214469435169307762601410054507581307143712827197102729172255582341259019582926017839184640439004418536133687852521072743550448484993159734928673214729233959910822647778787154652908525528171087279481713294114765983651303768616715943475653169406968995502342502428362211706");


    @RequestMapping("/")
    public String index() {
        return "this is a index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        System.out.println("/login");
        start = new Date().getTime();
        BigInteger sk = generateSK();
        BigInteger pk = g.modPow(sk, P);
        DHKey dhKey = new DHKey();
        String ID;
        do {
            ID = generateID();
            dhKey.setID(ID);
        }while (DHKeyManager.hasID(ID));
        dhKey.setG(g.toString());
        dhKey.setPk_server(pk.toString());
        dhKey.setBasic_client_id(basic_client_id.toString());
        dhKey.setRPCert(RPCert);
        Gson gson = new Gson();
        String responseBody = gson.toJson(dhKey);
        dhKey.setSK_server(sk.toString());
        DHKeyManager.put(dhKey.getID(), dhKey);
        point1 = new Date().getTime();
        response.setHeader("Access-Control-Allow-Origin", "*");
        return responseBody;
    }

    private BigInteger generateSK() {
        BigInteger sk = new BigInteger("2").pow(2047);
        SecureRandom r = new SecureRandom();
        for(int i=0;i<2047;i++){
            if(r.nextBoolean()){
                sk = sk.setBit(i);
            }
        }
        return sk;
    }

    private String generateID() {
        SecureRandom r = new SecureRandom();
        String ID = "";
        for(int i=0; i<24;i++){
            ID = ID + r.nextInt(10);
        }
        return ID;
    }

    @RequestMapping(value = "/uploadPK", method = RequestMethod.POST)
    public String uploadPK(@RequestBody String body, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println("/uploadPK");
        point2 = new Date().getTime();
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
            point3 = new Date().getTime();
            return "{\"result\":\"ok\", \"client_id\": \""+client_id.toString()+"\"}";
        }else {
            return "{\"result\":\"error\"}";
        }
    }

    @RequestMapping(value = "/register_finished", method = RequestMethod.POST)
    public ModelAndView register_finished(@RequestBody String body, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println("/register_finished");
        point4 = new Date().getTime();
        Gson gson = new Gson();
        RegistrationResult registrationResult = gson.fromJson(body, RegistrationResult.class);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(registrationResult.isResultOK()){
            DHKey dhKey = (DHKey)DHKeyManager.getByName(registrationResult.getID());
            if(registrationResult.getClient_id().equals(dhKey.getClient_id())){
                point5 = new Date().getTime();
                return new ModelAndView("redirect:http://10.10.81.42:8080/openid-connect-server-webapp/authorize?client_id=" + registrationResult.getClient_id() + "&redirect_uri=" + registrationResult.getRedirect_uri() + "&response_type=token&scope=openid%20email");
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    DecodedJWT decodeToken(String token){
        String estr = Util.bytes2HexString(Base64.getUrlDecoder().decode(e));//getDecoder().decode(e).toString();
        String nstr = Util.bytes2HexString(Base64.getUrlDecoder().decode(n));
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(nstr, 16), new BigInteger(estr, 16));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return null;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String authorization( HttpServletRequest request, HttpSession session, HttpServletResponse response){
        System.out.println("/authorization");
        response.setHeader("Access-Control-Allow-Origin", "*");
        point6 = new Date().getTime();
        String ID = request.getParameter("ID");
        String id_token = request.getParameter("id_token");
        DHKey dhKey = (DHKey)DHKeyManager.getByName(ID);
        DecodedJWT token = decodeToken(id_token);
        if(token != null) {

            BigInteger temp[] = ExtendEculid(new BigInteger(dhKey.getResult()), P.subtract(new BigInteger("1")));
            BigInteger _result = temp[1];
            BigInteger sub = new BigInteger(token.getSubject());
            BigInteger userIdentity = sub.modPow(_result, P);
            UserInfo localUserInfo = UserManager.getUserByID(userIdentity.toString());
            if (token.getAudience().contains(dhKey.getClient_id())) {
                if (localUserInfo != null) {
                    if (userIsValidate(dhKey, localUserInfo, userIdentity.toString())) {
                        point7 = new Date().getTime();
                        return "{\"result\":\"ok\"}";
                    } else
                        return "{\"result\":\"error\"}";
                } else {
                    //token.init();
                    UserInfo user = new UserInfo();
                    user.setID(userIdentity.toString());
                    UserManager.setUser(user);
                    return "{\"result\":\"register\"}";
                }
            }
        }
        return "{\"result\":\"error\"}";
    }
    @RequestMapping(value = "/end", method = RequestMethod.GET)
    public String end( HttpServletRequest request, HttpSession session, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        end = new Date().getTime();
        count++;


        System.out.println("Negotiation: " + (point1 + point3 - point2 - start));
        System.out.println("NegotiationTotal : : " + (point3 - start));



//
        long negotiation = point3 - start;
        long registration = point5 - point3;
        long tokenObtaining = point7 - point5;
        long total1 = negotiation + registration + tokenObtaining;
        totalNegotiation += negotiation;
        totalRegistration += registration;
        totalTokenObtaining += tokenObtaining;
        long totalTotal = totalNegotiation + totalRegistration + totalTokenObtaining;
        System.out.println(count);
        System.out.println("negotiation: " + totalNegotiation/count + "ms");
        System.out.println("registration: " + totalRegistration/count + "ms");
        System.out.println("tokenObtaining: " + totalTokenObtaining/count + "ms");
        System.out.println("total: " + totalTotal/count + "ms");
        return "end";
    }

    static BigInteger[] ExtendEculid(BigInteger a, BigInteger b)
    {
        BigInteger x,  y;
        if (b.compareTo(new BigInteger("0"))==0)
        {
            x = new BigInteger("1");
            y = new BigInteger("0");
            BigInteger[] t = new BigInteger[3];
            t[0] = a; t[1] = x; t[2] = y;
            return t;
        }
        BigInteger[] t = ExtendEculid(b, a.mod(b));
        BigInteger result = t[0];
        x = t[1];
        y = t[2];
        BigInteger temp = x;
        x = y;
        y = temp.subtract(a.divide(b).multiply(y));
        BigInteger[] t1 = new BigInteger[3];
        t1[0] = result; t1[1] = x; t1[2] = y;
        return t1;
    }


    private boolean userIsValidate(DHKey dhKey, UserInfo userInfo, String sub) {
        if(userInfo.getID().equals(sub)){
            return true;
        }
        return false;
    }

//    @RequestMapping("/login1")
//    public ModelAndView login1(HttpServletRequest request, HttpSession session, HttpServletResponse response){
//        return new ModelAndView("redirect:http://localhost:8080/openid-connect-server-webapp/authorize?client_id=client&redirect_uri=http://localhost:8090/authorization1&response_type=token&scope=openid");
//    }
//
//    @RequestMapping("/authorization1")
//    public String authorization1(HttpServletRequest request, HttpSession session, HttpServletResponse response){
//        String id_token = "eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoicGYzZVdpb1prc0V3MGVMbGFZYzJGQSIsInN1YiI6IjEyNDUwLkFTREZKV0ZBIiwiYXVkIjoiY2xpZW50Iiwia2lkIjoicnNhMSIsImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MDgwXC9vcGVuaWQtY29ubmVjdC1zZXJ2ZXItd2ViYXBwXC8iLCJleHAiOjE1NDI2MjA1NTIsImlhdCI6MTU0MjYxOTk1MiwianRpIjoiNjdkODBhNGItMmU1Ni00YzA0LTkyNzYtMjg2OGRmNGY5ZDc2In0.cS70HvBMoAseXSxSX-Ks0EgGweNWMA_ZOVi9dOVqLQYDR6UBAJDvHl9C9XPqkauZufQp1O0pZS3aPOj7Rg3WQO0XgqnDLthU4Xa6V3D0BzC6PCiXQpzbO6Srjw5dYugbYclFLm7pGupPycdTBwssvP3UbstKHT9XRhDEqclmS54dxnwY1Bdpr3vH52uK1xUYYliHvcFHf3diwZWNVQm7pB6qKnURbx4Alq71nqzifczRn1-bHutOpRIj6GOOls9YfNeHJ1avPo2nuEB8VVdaQZaTrhpAuZNwfS6mPOQ0Ui92DBayaJ3zkRHh6VzmDbEJAiStEtYMUq4sKGkDJy8ZVQ";//request.getParameter("id_token");
//        Token token = new Token(id_token);
//        UserInfo localUserInfo = UserManager.getUserByID(token.getBody().getSub());
//        if(token.getBody().getAud().equals("client")) {
//            if (token.isValid()) {
//                if (localUserInfo != null) {
//                        return "{\"result\":\"ok\"}";
//                } else {
//                    token.init();
//                    UserInfo user = new UserInfo();
//                    user.setID(token.getBody().getSub());
////                user.setRegister_uid(userIdentity.toString());
////                user.setSk_server(dhKey.getSk_server());
//                    UserManager.setUser(user);
//                    return "{\"result\":\"register\"}";
//                }
//            } else {
//                return "{\"result\":\"error\"}";
//            }
//        }
//        return "ok";
//    }


}