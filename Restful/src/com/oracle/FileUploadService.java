package com.oracle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("fileupload")
public class FileUploadService {

 String response = "";

 @POST
 @Path("upload")
 @Consumes(MediaType.MULTIPART_FORM_DATA)
 public Response uploadFile(@FormDataParam("file") InputStream inputStream,
   @FormDataParam("file") FormDataContentDisposition file) {

  try {
   final String FILE_DESTINATION = "C://jtc//" + file.getFileName();
   File f = new File(FILE_DESTINATION);
   OutputStream outputStream = new FileOutputStream(f);
   int size = 0;
   byte[] bytes = new byte[1024];
   while ((size = inputStream.read(bytes)) != -1) {
    outputStream.write(bytes, 0, size);
   }
   outputStream.flush();
   outputStream.close();

   response = "File uploaded " + FILE_DESTINATION;
  } catch (Exception e) {
   e.printStackTrace();
  }

  return Response.status(200).entity(response).build();
 }
}