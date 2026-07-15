package com.homecam.network;

import com.homecam.model.Command;import com.homecam.utils.Validators;import org.json.JSONObject;import org.webrtc.IceCandidate;import org.webrtc.SessionDescription;import java.io.*;import java.net.*;import java.nio.charset.StandardCharsets;

/** HTTPS client for Cloudflare Worker signaling; it sends metadata only and never media bytes. */
public final class WorkerClient { private final String baseUrl; public WorkerClient(String baseUrl){this.baseUrl=baseUrl.replaceAll("/+$","");}
 public JSONObject postOffer(String d,String t,SessionDescription s)throws IOException{return post("/offer",base(d,t).put("type",s.type.canonicalForm()).put("sdp",s.description));}
 public JSONObject postAnswer(String d,String t,SessionDescription s)throws IOException{return post("/answer",base(d,t).put("type",s.type.canonicalForm()).put("sdp",s.description));}
 public JSONObject postCandidate(String d,String t,IceCandidate c)throws IOException{if(!Validators.isValidIce(c))throw new IOException("Invalid ICE candidate");return post("/candidate",base(d,t).put("sdpMid",c.sdpMid).put("sdpMLineIndex",c.sdpMLineIndex).put("candidate",c.sdp));}
 public JSONObject postCommand(String d,String t,Command c)throws IOException{return post("/command",base(d,t).put("command",c.name()).put("nonce",System.nanoTime()));}
 public JSONObject status(String d,String t)throws IOException{return request("GET","/status?deviceId="+URLEncoder.encode(d,"UTF-8")+"&token="+URLEncoder.encode(t,"UTF-8"),null);} 
 private JSONObject base(String d,String t)throws IOException{if(!Validators.isValidDeviceId(d)||!Validators.isValidToken(t))throw new IOException("Invalid signaling identity");return new JSONObject().put("deviceId",d).put("token",t);} 
 private JSONObject post(String path,JSONObject body)throws IOException{return request("POST",path,body.toString());}
 private JSONObject request(String method,String path,String body)throws IOException{HttpURLConnection c=(HttpURLConnection)new URL(baseUrl+path).openConnection();c.setRequestMethod(method);c.setConnectTimeout(10000);c.setReadTimeout(10000);c.setRequestProperty("Accept","application/json"); if(body!=null){byte[] b=body.getBytes(StandardCharsets.UTF_8);c.setDoOutput(true);c.setRequestProperty("Content-Type","application/json");c.setFixedLengthStreamingMode(b.length);try(OutputStream o=c.getOutputStream()){o.write(b);}} int code=c.getResponseCode();InputStream in=code>=200&&code<300?c.getInputStream():c.getErrorStream();String text=read(in); if(code<200||code>=300)throw new IOException("Worker HTTP "+code+": "+text); try{return new JSONObject(text.isEmpty()?"{}":text);}catch(Exception e){throw new IOException("Invalid worker JSON",e);} }
 private static String read(InputStream in)throws IOException{if(in==null)return"";ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] buf=new byte[2048];int n;while((n=in.read(buf))!=-1)out.write(buf,0,n);return out.toString("UTF-8");}}
