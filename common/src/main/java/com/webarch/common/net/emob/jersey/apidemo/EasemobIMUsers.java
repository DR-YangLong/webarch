/*
 * Copyright (c) 2015 浼犲寲绉戞妧鏈嶅姟鏈夐檺鍏徃(Transfar Group) All rights reserved
 */

package com.webarch.common.net.emob.jersey.apidemo;

import com.webarch.common.net.emob.comm.Constants;
import com.webarch.common.net.emob.comm.HTTPMethod;
import com.webarch.common.net.emob.comm.Roles;
import com.webarch.common.net.emob.jersey.utils.JerseyUtils;
import com.webarch.common.net.emob.jersey.vo.ClientSecretCredential;
import com.webarch.common.net.emob.jersey.vo.Credential;
import com.webarch.common.net.emob.jersey.vo.EndPoints;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API Demo : 鐢ㄦ埛浣撶郴闆嗘垚 Jersey2.9瀹炵幇
 *
 * Doc URL: http://www.easemob.com/docs/rest/userapi/
 *
 * @author Lynch 2014-09-09
 *
 */
public class EasemobIMUsers {

	private static final Logger LOGGER = LoggerFactory.getLogger(EasemobIMUsers.class);
	private static final String APPKEY = Constants.APPKEY;
	private static final JsonNodeFactory factory = new JsonNodeFactory(false);

    // 閫氳繃app鐨刢lient_id鍜宑lient_secret鏉ヨ幏鍙朼pp绠＄悊鍛榯oken
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    public static void main(String[] args) {
        /**
         * 娉ㄥ唽IM鐢ㄦ埛[鍗曚釜]
         */
        ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username","kenshinnuser100");
        datanode.put("password", Constants.DEFAULT_PASSWORD);
        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
        if (null != createNewIMUserSingleNode) {
            LOGGER.info("娉ㄥ唽IM鐢ㄦ埛[鍗曚釜]: " + createNewIMUserSingleNode.toString());
        }

        /**
         * IM鐢ㄦ埛鐧诲綍
         */
        ObjectNode imUserLoginNode = imUserLogin(datanode.get("username").asText(), datanode.get("password").asText());
        if (null != imUserLoginNode) {
            LOGGER.info("IM鐢ㄦ埛鐧诲綍: " + imUserLoginNode.toString());
        }

        /**
         * 娉ㄥ唽IM鐢ㄦ埛[鎵归噺鐢熸垚鐢ㄦ埛鐒跺悗娉ㄥ唽]
         */
        String usernamePrefix = "kenshinnuser";
        Long perNumber = 10l;
        Long totalNumber = 100l;
        ObjectNode createNewIMUserBatchGenNode = createNewIMUserBatchGen(usernamePrefix, perNumber, totalNumber);
        if (null != createNewIMUserBatchGenNode) {
            LOGGER.info("娉ㄥ唽IM鐢ㄦ埛[鎵归噺]: " + createNewIMUserBatchGenNode.toString());
        }

        /**
         * 鑾峰彇IM鐢ㄦ埛[涓婚敭鏌ヨ]
         */
        String userName = "kenshinnuser100";
        ObjectNode getIMUsersByUserNameNode = getIMUsersByUserName(userName);
        if (null != getIMUsersByUserNameNode) {
            LOGGER.info("鑾峰彇IM鐢ㄦ埛[涓婚敭鏌ヨ]: " + getIMUsersByUserNameNode.toString());
        }

        /**
         * 閲嶇疆IM鐢ㄦ埛瀵嗙爜 鎻愪緵绠＄悊鍛榯oken
         */
		String username = "kenshinnuser100";
        ObjectNode json2 = JsonNodeFactory.instance.objectNode();
        json2.put("newpassword", Constants.DEFAULT_PASSWORD);
        ObjectNode modifyIMUserPasswordWithAdminTokenNode = modifyIMUserPasswordWithAdminToken(username, json2);
        if (null != modifyIMUserPasswordWithAdminTokenNode) {
            LOGGER.info("閲嶇疆IM鐢ㄦ埛瀵嗙爜 鎻愪緵绠＄悊鍛榯oken: " + modifyIMUserPasswordWithAdminTokenNode.toString());
        }
        ObjectNode imUserLoginNode2 = imUserLogin(username, json2.get("newpassword").asText());
        if (null != imUserLoginNode2) {
            LOGGER.info("閲嶇疆IM鐢ㄦ埛瀵嗙爜鍚�IM鐢ㄦ埛鐧诲綍: " + imUserLoginNode2.toString());
        }

        /**
         * 娣诲姞濂藉弸[鍗曚釜]
         */
        String ownerUserName = username;
        String friendUserName = "kenshinnuser099";
        ObjectNode addFriendSingleNode = addFriendSingle(ownerUserName, friendUserName);
        if (null != addFriendSingleNode) {
            LOGGER.info("娣诲姞濂藉弸[鍗曚釜]: " + addFriendSingleNode.toString());
        }

        /**
         * 鏌ョ湅濂藉弸
         */
        ObjectNode getFriendsNode = getFriends(ownerUserName);
        if (null != getFriendsNode) {
            LOGGER.info("鏌ョ湅濂藉弸: " + getFriendsNode.toString());
        }

        /**
         * 瑙ｉ櫎濂藉弸鍏崇郴
         **/
        ObjectNode deleteFriendSingleNode = deleteFriendSingle(ownerUserName, friendUserName);
        if (null != deleteFriendSingleNode) {
            LOGGER.info("瑙ｉ櫎濂藉弸鍏崇郴: " + deleteFriendSingleNode.toString());
        }

        /**
         * 鍒犻櫎IM鐢ㄦ埛[鍗曚釜]
         */
        ObjectNode deleteIMUserByUserNameNode = deleteIMUserByUserName(userName);
        if (null != deleteIMUserByUserNameNode) {
            LOGGER.info("鍒犻櫎IM鐢ㄦ埛[鍗曚釜]: " + deleteIMUserByUserNameNode.toString());
        }

        /**
         * 鍒犻櫎IM鐢ㄦ埛[鎵归噺]
         */
        Long limit = 2l;
        ObjectNode deleteIMUserByUsernameBatchNode = deleteIMUserByUsernameBatch(limit, null);
        if (null != deleteIMUserByUsernameBatchNode) {
            LOGGER.info("鍒犻櫎IM鐢ㄦ埛[鎵归噺]: " + deleteIMUserByUsernameBatchNode.toString());
        }
    }

    /**
	 * 娉ㄥ唽IM鐢ㄦ埛[鍗曚釜]
	 *
	 * 缁欐寚瀹欰ppKey鍒涘缓涓�釜鏂扮殑鐢ㄦ埛
	 *
	 * @param dataNode
	 * @return
	 */
	public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		objectNode.removeAll();

		// check properties that must be provided
		if (null != dataNode && !dataNode.has("username")) {
			LOGGER.error("Property that named username must be provided .");

			objectNode.put("message",
					"Property that named username must be provided .");

			return objectNode;
		}
		if (null != dataNode && !dataNode.has("password")) {
			LOGGER.error("Property that named password must be provided .");

			objectNode.put("message",
					"Property that named password must be provided .");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = EndPoints.USERS_TARGET.resolveTemplate("org_name",
					APPKEY.split("#")[0]).resolveTemplate("app_name",
					APPKEY.split("#")[1]);

			objectNode = JerseyUtils.sendRequest(webTarget, dataNode, credential, HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 娉ㄥ唽IM鐢ㄦ埛[鎵归噺]
	 *
	 * 缁欐寚瀹欰ppKey鍒涘缓涓�壒鐢ㄦ埛
	 *
	 * @param dataArrayNode
	 * @return
	 */
	public static ObjectNode createNewIMUserBatch(ArrayNode dataArrayNode) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		// check properties that must be provided
		if (dataArrayNode.isArray()) {
			for (JsonNode jsonNode : dataArrayNode) {
				if (null != jsonNode && !jsonNode.has("username")) {
					LOGGER.error("Property that named username must be provided .");

					objectNode.put("message", "Property that named username must be provided .");

					return objectNode;
				}
				if (null != jsonNode && !jsonNode.has("password")) {
					LOGGER.error("Property that named password must be provided .");

					objectNode.put("message", "Property that named password must be provided .");

					return objectNode;
				}
			}
		}

		try {

			JerseyWebTarget webTarget = EndPoints.USERS_TARGET.resolveTemplate("org_name",
					APPKEY.split("#")[0]).resolveTemplate("app_name", APPKEY.split("#")[1]);

			objectNode = JerseyUtils.sendRequest(webTarget, dataArrayNode, credential, HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 娉ㄥ唽IM鐢ㄦ埛[鎵归噺鐢熸垚鐢ㄦ埛鐒跺悗娉ㄥ唽]
	 *
	 * 缁欐寚瀹欰ppKey鍒涘缓涓�壒鐢ㄦ埛
	 *
	 * @param usernamePrefix
	 *            鐢熸垚鐢ㄦ埛鍚嶇殑鍓嶇紑
	 * @param perNumber
	 *            鎵归噺娉ㄥ唽鏃朵竴娆℃敞鍐岀殑鏁伴噺
	 * @param totalNumber
	 *            鐢熸垚鐢ㄦ埛娉ㄥ唽鐨勭敤鎴锋�鏁�
	 * @return
	 */
	public static ObjectNode createNewIMUserBatchGen(String usernamePrefix,
			Long perNumber, Long totalNumber) {
		ObjectNode objectNode = factory.objectNode();

		if (totalNumber == 0 || perNumber == 0) {
			return objectNode;
		}
		ArrayNode genericArrayNode = EasemobIMUsers.genericArrayNode(
				usernamePrefix, totalNumber);
		if (totalNumber <= perNumber) {
			objectNode = EasemobIMUsers.createNewIMUserBatch(genericArrayNode);
		} else {

			for (int i = 0; i < genericArrayNode.size(); i++) {
				ArrayNode tmpArrayNode = factory.arrayNode();
				tmpArrayNode.add(genericArrayNode.get(i));
				// 300 records on one migration
				if ((i + 1) % perNumber == 0) {
					objectNode = EasemobIMUsers
							.createNewIMUserBatch(genericArrayNode);
					tmpArrayNode.removeAll();
					continue;
				}

				// the rest records that less than the times of 300
				if (i > (genericArrayNode.size() / perNumber * perNumber - 1)) {
					objectNode = EasemobIMUsers
							.createNewIMUserBatch(genericArrayNode);
					tmpArrayNode.removeAll();
				}
			}
		}

		return objectNode;
	}

	/**
	 * 鑾峰彇IM鐢ㄦ埛
	 *
	 * @param userName
	 *            鐢ㄦ埛涓婚敭锛歶sername鎴栬�uuid
	 * @return
	 */
	public static ObjectNode getIMUsersByUserName(String userName) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		// check properties that must be provided
		if (StringUtils.isEmpty(userName)) {
			LOGGER.error("The primaryKey that will be useed to query must be provided .");

			objectNode
					.put("message",
							"The primaryKey that will be useed to query must be provided .");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(userName);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credential,
					HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 鍒犻櫎IM鐢ㄦ埛[鍗曚釜]
	 *
	 * 鍒犻櫎鎸囧畾AppKey涓婭M鍗曚釜鐢ㄦ埛
	 *
	 *
	 * @param userName
	 * @return
	 */
	public static ObjectNode deleteIMUserByUserName(String userName) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(userName);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credential,
					HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 鍒犻櫎IM鐢ㄦ埛[鎵归噺]
	 *
	 * 鎵归噺鎸囧畾AppKey涓嬪垹闄M鐢ㄦ埛
	 *
	 * @param limit
	 * @param queryStr
	 * @return
	 */
	public static ObjectNode deleteIMUserByUsernameBatch(Long limit,
			String queryStr) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.queryParam("ql", queryStr)
					.queryParam("limit", String.valueOf(limit));

			objectNode = JerseyUtils.sendRequest(webTarget, null, credential,
					HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 閲嶇疆IM鐢ㄦ埛瀵嗙爜 鎻愪緵绠＄悊鍛榯oken
	 *
	 * @param userName
	 * @param dataObjectNode
	 * @return
	 */
	public static ObjectNode modifyIMUserPasswordWithAdminToken(
			String userName, ObjectNode dataObjectNode) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(userName)) {
			LOGGER.error("Property that named userName must be provided锛宼he value is username or uuid of imuser.");

			objectNode
					.put("message",
							"Property that named userName must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}

		if (null != dataObjectNode && !dataObjectNode.has("newpassword")) {
			LOGGER.error("Property that named newpassword must be provided .");

			objectNode.put("message",
					"Property that named newpassword must be provided .");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(userName).path("password");

			objectNode = JerseyUtils.sendRequest(webTarget, dataObjectNode,
					credential, HTTPMethod.METHOD_PUT, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 娣诲姞濂藉弸[鍗曚釜]
	 *
	 * @param ownerUserName
	 * @param friendUserName
	 *
	 * @return
	 */
	public static ObjectNode addFriendSingle(String ownerUserName,
			String friendUserName) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(ownerUserName)) {
			LOGGER.error("Your userName must be provided锛宼he value is username or uuid of imuser.");

			objectNode
					.put("message",
							"Your userName must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}

		if (StringUtils.isEmpty(friendUserName)) {
			LOGGER.error("The userName of friend must be provided锛宼he value is username or uuid of imuser.");

			objectNode
					.put("message",
							"The userName of friend must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = EndPoints.USERS_ADDFRIENDS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.resolveTemplate("ownerUserName", ownerUserName)
					.resolveTemplate("friendUserName",
							friendUserName);

			ObjectNode body = factory.objectNode();
			objectNode = JerseyUtils.sendRequest(webTarget, body, credential,
					HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 瑙ｉ櫎濂藉弸鍏崇郴
	 *
	 * @param ownerUserName
	 * @param friendUserName
	 *
	 * @return
	 */
	public static ObjectNode deleteFriendSingle(String ownerUserName,
			String friendUserName) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(ownerUserName)) {
			LOGGER.error("Your userName must be provided锛宼he value is username or uuid of imuser.");

			objectNode.put("message",
							"Your userName must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}

		if (StringUtils.isEmpty(friendUserName)) {
			LOGGER.error("The userName of friend must be provided锛宼he value is username or uuid of imuser.");

			objectNode
					.put("message",
							"The userName of friend must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = EndPoints.USERS_ADDFRIENDS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.resolveTemplate("ownerUserName", ownerUserName)
					.resolveTemplate("friendUserName",
							friendUserName);

			ObjectNode body = factory.objectNode();
			objectNode = JerseyUtils.sendRequest(webTarget, body, credential, HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 鏌ョ湅濂藉弸
	 *
	 * @param ownerUserName
	 *
	 * @return
	 */
	public static ObjectNode getFriends(String ownerUserName) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(ownerUserName)) {
			LOGGER.error("Your userName must be provided锛宼he value is username or uuid of imuser.");

			objectNode.put("message",
							"Your userName must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = EndPoints.USERS_ADDFRIENDS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.resolveTemplate("ownerUserName", ownerUserName)
					.resolveTemplate("friendUserName", "");

			ObjectNode body = factory.objectNode();
			objectNode = JerseyUtils.sendRequest(webTarget, body, credential, HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * IM鐢ㄦ埛鐧诲綍
	 *
	 * @param ownerUserName
	 * @param password
     *
	 * @return
	 */
	public static ObjectNode imUserLogin(String ownerUserName, String password) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}
		if (StringUtils.isEmpty(ownerUserName)) {
			LOGGER.error("Your userName must be provided锛宼he value is username or uuid of imuser.");

			objectNode.put("message",
							"Your userName must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}
		if (StringUtils.isEmpty(password)) {
			LOGGER.error("Your password must be provided锛宼he value is username or uuid of imuser.");

			objectNode.put("message",
							"Your password must be provided锛宼he value is username or uuid of imuser.");

			return objectNode;
		}

		try {
			ObjectNode dataNode = factory.objectNode();
			dataNode.put("grant_type", "password");
			dataNode.put("username", ownerUserName);
			dataNode.put("password", password);

			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new BasicNameValuePair("Content-Type", "application/json"));

			objectNode = JerseyUtils.sendRequest(EndPoints.TOKEN_APP_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1]),
					dataNode, null, HTTPMethod.METHOD_POST, headers);

		} catch (Exception e) {
			throw new RuntimeException(	"Some errors ocuured while fetching a token by usename and passowrd .");
		}

		return objectNode;
	}

	/**
	 * 鎸囧畾鍓嶇紑鍜屾暟閲忕敓鎴愮敤鎴峰熀鏈暟鎹�
	 * 
	 * @param usernamePrefix
	 * @param number
	 * @return
	 */
	private static ArrayNode genericArrayNode(String usernamePrefix, Long number) {
		ArrayNode arrayNode = factory.arrayNode();
		for (int i = 0; i < number; i++) {
			ObjectNode userNode = factory.objectNode();
			userNode.put("username", usernamePrefix + "_" + i);
			userNode.put("password", Constants.DEFAULT_PASSWORD);

			arrayNode.add(userNode);
		}

		return arrayNode;
	}

}