package library.catAPILib;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import org.testng.Assert;

import library.MyBaseTest;
import library.MyHttpRequest;
import library.Utils;

import enums.RequestType;
import enums.UserType;
import library.Logging;

public class TheCat extends MyBaseTest {
	private String URL = library.MyBaseTest.catAPIBaseURL + "v1/images/";
	private String imageId = null;
	private String imageURL = null;
	private String subjectId = null;
	private String originalFileName = null;
	private boolean isApproved = false;
	public MyHttpRequest catHttpRequest = null;

	public TheCat createCat(String testMethodName, String user, String imagePath) throws Throwable {
		try {
			Utils utils = new Utils(RequestType.POST, URL + "upload", getUserTypeByEnumID(user));

			FileBody fileBody = new FileBody(new File(imagePath), ContentType.IMAGE_JPEG);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addPart("file", fileBody);
			HttpEntity entity = builder.build();
			utils.httpRequest.setFormEntity(entity);
			
			
			utils.httpRequest.buildAndExecute();
			this.catHttpRequest = utils.httpRequest;
			if(new JSONObject(catHttpRequest.getResponseBody()).has("id")) MyBaseTest.deleteHttpRequestMap.put(utils.httpRequest, "id");

			Logging.log.info("Response for " + testMethodName + ": " + utils.httpRequest.getResponseBody());
			Logging.log.info("Response Code: " + utils.httpRequest.getStatusCode());

			//Assert.assertEquals(utils.httpRequest.getStatusCode(), "201");
			if(new JSONObject(catHttpRequest.getResponseBody()).has("id")) this.setImageId(new JSONObject(catHttpRequest.getResponseBody()).get("id").toString());
			if(new JSONObject(catHttpRequest.getResponseBody()).has("url")) this.setImageURL(new JSONObject(catHttpRequest.getResponseBody()).get("url").toString());
			if(new JSONObject(catHttpRequest.getResponseBody()).has("sub_id")) this.setSubjectId(new JSONObject(catHttpRequest.getResponseBody()).get("sub_id").toString());
			if(new JSONObject(catHttpRequest.getResponseBody()).has("original_filename")) this.setOriginalFileName(new JSONObject(catHttpRequest.getResponseBody()).get("original_filename").toString());
			if(new JSONObject(catHttpRequest.getResponseBody()).has("approved")) if(new JSONObject(catHttpRequest.getResponseBody()).getInt("approved") == 1) this.setApproved(true);
		} catch (Exception e) {
			throw (new Throwable(e));
		}
		return new TheCat();
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

}
