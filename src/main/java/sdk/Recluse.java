package sdk;

import sdk.Bean.DHKey;
import sdk.Bean.DHKeyManager;
import sdk.Tools.Util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class Recluse {
    String e = "AQAB";
    String n = "qt6yOiI_wCoCVlGO0MySsez0VkSqhPvDl3rfabOslx35mYEO-n4ABfIT5Gn2zN-CeIcOZ5ugAXvIIRWv5H55-tzjFazi5IKkOIMCiz5__MtsdxKCqGlZu2zt-BLpqTOAPiflNPpM3RUAlxKAhnYEqNha6-allPnFQupnW_eTYoyuzuedT7dSp90ry0ZcQDimntXWeaSbrYKCj9Rr9W1jn2uTowUuXaScKXTCjAmJVnsD75JNzQfa8DweklTyWQF-Y5Ky039I0VIu-0CIGhXY48GAFe2EFb8VpNhf07DP63p138RWQ1d3KPEM9mYJVpQC68j3wzDQYSljpLf9by7TGw";
    String RPCert = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3N1ZXIiOiJodHRwOi8vMTAuMTAuODEuNDI6ODA4MCIsImJhc2ljX2NsaWVudF9pZCI6IjQwNDE0MjY2NDU2MTI1MDM4NzMyNzcwMDM5ODMwMzg2NjY0NTQ2NTA1NzYxNjAzMjM5NDA1NjY0NTQ3MTU1NTk0OTE4NjczOTI0MDI4NzM2NzMzNjQ3MzIxNjI3NTExMTMwMDQzOTMxODE3MTE4NDI3ODA0Mzk3MjMzNDI5Mjg0MTY1NDQyNDUxMzUyMTk5MDc4ODgxMzIzMDM3OTEzMzY4OTcwNjAzMDk1MTA1NjcwNzAzMjA5MTU4NzcxMTgxMjcyNDAyMzE1NDY2MjA3NDc2NzY1MDE3Mjg0MzQ2MzE4NDIyNTA5OTQ5MTYwNDI3NTI1MTMxMDEyMzI3NDgyNjY1NTQ1NTQ1NDcxMTkzODQwNTA5OTg5NjA5MjE4MzUwMTI1NTM5MTU2MDcyNDg5NDA0OTAzOTE2NjUxMTk0ODAxNTgyMDYzNjE4OTkxNTQxMDQ4NTY4MTQ4MjkyMTQ0Njk0MzUxNjkzMDc3NjI2MDE0MTAwNTQ1MDc1ODEzMDcxNDM3MTI4MjcxOTcxMDI3MjkxNzIyNTU1ODIzNDEyNTkwMTk1ODI5MjYwMTc4MzkxODQ2NDA0MzkwMDQ0MTg1MzYxMzM2ODc4NTI1MjEwNzI3NDM1NTA0NDg0ODQ5OTMxNTk3MzQ5Mjg2NzMyMTQ3MjkyMzM5NTk5MTA4MjI2NDc3Nzg3ODcxNTQ2NTI5MDg1MjU1MjgxNzEwODcyNzk0ODE3MTMyOTQxMTQ3NjU5ODM2NTEzMDM3Njg2MTY3MTU5NDM0NzU2NTMxNjk0MDY5Njg5OTU1MDIzNDI1MDI0MjgzNjIyMTE3MDYiLCJyZWRpcmVjdF91cmkiOiJodHRwOi8vMTAuMTAuODEuODk6ODA4MCIsImNsaWVudF9uYW1lIjoicHJvdG90eXBlIiwiaWF0IjoxNTE2MjM5MDIyfQ.i59VxZaZRpxhsw6f/jr2Ko+crnf/qYmUeAvi1SKxe9dARmmKwSyGeLc7KsAAofKfIkoP9sSdxR/mvXE5qCM/XSoDHoZjn49WxYprrCWlI7CbYnW/FtasbpEpnI6M0AW7vcoFAEty2ePJbKChJw5/AxjBTdN6EVxg2SJOuzm2RkeLJSC5jNm7qY6cFmWE/ZMzlO5vZ+iVtPcoU079Fc5f9v8keIdBwyEZkwv5d/KivCZO223zzPKcE2wXoTlqefqrHbvSkvVKHJe0xeYWdeYcTM6HnHP8IW2covVYL6NYkVq6jwaPGkLO9kimsuBSVgEbKnwN8EexqLc4HGWojTlAOA==";
    BigInteger P = new BigInteger("27449016760001930830125617668247859359745430986704432309085929677995807338305678419885652753794242496407976182871494825520552812933077776662415387179356727792234451654055658177210561689664280863983972105655690337291450879107894692596167210983185326590416340066192826004270803522574046051860102165172244586056707834948333241005980694712476760216030160395462421297530733396614124258707965156976358959843617034441619164457098759725517618250918992025137481460788874501395889763568442978210065547845149324569983455235117868998436516548293413794803510612425480720620581614363153446128915785524341773999191504845709308817083");
    BigInteger g = new BigInteger("3");
    BigInteger basic_client_id = new BigInteger("4041426645612503873277003983038666454650576160323940566454715559491867392402873673364732162751113004393181711842780439723342928416544245135219907888132303791336897060309510567070320915877118127240231546620747676501728434631842250994916042752513101232748266554554547119384050998960921835012553915607248940490391665119480158206361899154104856814829214469435169307762601410054507581307143712827197102729172255582341259019582926017839184640439004418536133687852521072743550448484993159734928673214729233959910822647778787154652908525528171087279481713294114765983651303768616715943475653169406968995502342502428362211706");
    RecluseToken recluseToken;
    public Recluse(){
    }
    private BigInteger generateSK() {
        BigInteger sk = new BigInteger("2").pow(2047);
        SecureRandom r = new SecureRandom();
        for(int i=0;i<256;i++){
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
    public String buildNegotiationResponse(HttpServletResponse response){
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
        response.setHeader("Access-Control-Allow-Origin", "*");
        return responseBody;

    }
    public void receiveToken(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String ID = request.getParameter("ID");
        String id_token = request.getParameter("id_token");
        DHKey dhKey = (DHKey) DHKeyManager.getByName(ID);
        DecodedJWT token = decodeToken(id_token);
        recluseToken = new RecluseToken();
        if(token != null) {
            if(token.getAudience().contains(dhKey.getClient_id())){
                recluseToken.setValid(true);
            }else {
                recluseToken.setValid(false);
            }
            BigInteger temp[] = ExtendEculid(new BigInteger(dhKey.getResult()), P.subtract(new BigInteger("1")));
            BigInteger _result = temp[1];
            BigInteger sub = new BigInteger(token.getSubject());
            BigInteger userIdentity = sub.modPow(_result, P);
            recluseToken.init(token, userIdentity.toString());
        }else {
            recluseToken.setValid(false);
        }
    }
    public BigInteger[] ExtendEculid(BigInteger a, BigInteger b)
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
    public RecluseToken getToken() {
        return recluseToken;
    }
}
