
����΢��API��java�棩
============================
�޸����
1���ӿڷ��ؽ������json����ʽ��װ
2���޸�WeiboResponse�п��ַ���("")�����жϵ�bug
3���޸���JSONObject�з��ؿն��������
4��������ֱ���ļ��ϴ��ӿ�
5������Sdk��block�ӿ������ַ�Ĵ���
6���޸�createAtʱ���ʽת����bug��
7���������û���ǩ�ӿڡ���˽���ýӿڡ�����ɾ���ӿڡ�
8��User/hot��statuses/reset_count�Լ�repost_timeline��repost_by_me�ӿڡ� 
9����friendsstatuses��followstatuses��֧��page��������Ϊcursor������
10���ر�log�����


���ýӿ�ʵ����
��ʹ��ǰ���޸� Weibo.java �� 
 	public static final String CONSUMER_KEY = "";
	public static final String CONSUMER_SECRET = "";
 ��д��ʵ������� CONSUMER_KEY �� CONSUMER_SECRET��

1����ȡ���¹���΢���б�
	�ο���weibo4j.examples.GetTimelines �е�getPublicTimeline()����

2������΢��
	�ο���weibo4j.examples.Update �е�updateStatus(...)����

3����������
	�ο���weibo4j.examples.Update �е�updateComment(...)����

4��ɾ������
	�ο���weibo4j.examples.Update �е�destroyComment(...)����

5������˽����Ϣ
	�ο���weibo4j.examples.DirectMessage ��

6�������ͼƬ��΢��
	�ο���weibo4j.examples.OAuthUploadByFile �е�uploadStatus(status,file)����

7�������û�ͷ��
	�ο���weibo4j.examples.OAuthUpdateProfile��

8��OAuth����Ӧ�÷���
	���裺 1> ��weibo4j.examples.OAuthUpdate ������֤��URL���õ�pin
		  2> ��weibo4j.examples.OAuthUpdate 	����pin��Ȼ��õ�AccessToken
		  3> ���ɷ����û���΢�����ο���weibo4j.examples.OAuthUpdateTwo

9��OAuth WEBӦ�÷���
	���裺 
					1> ��weibo4j.examples.WebOAuth ��backurl��������OAuth��֤�����RequestToken
				  2> ��callback �ĵ�ַ���棬���ܵ�oauth_verifier������Ȼ���ٷ�һ�����󣬼��ɻ��AccessToken
				  3> ��AccessToken�����û���΢��
	ʵ��˵����
	
		��webĿ¼����������jspҳ�棺
		call.jsp 
					�Ƿ���request�������ڵھ��еĲ�����http://localhost:8080/callback.jsp���ǻص���ַ
				  ����ȡ�ɹ���RequestToken����session�����ض����û���֤��ַ
	  callback.jsp 
	  			���յ�oauth_verifier��������session�����õ�RequestToken���������ȡAccessToken
	    		��ȡ���󼴿ɶ��û�΢�����в������������Ƿ���΢��
	
	���Ի�����
		
		�����п��Խ�call.jsp��  callback.jspֱ�ӷŵ�tomcat�ĸ�Ŀ¼���棺webapps\ROOT
		����ʵ����Ŀ����õ�classes�ļ���libĿ¼������ROOT\WEB-INF����
		����tomcat������http://localhost:8080/call.jsp?opt=1	
	ע��Ҳ���Դ��war������ע��call.jsp�����callback������Ҫ����Ӧ�޸�


=============================================================================
�����ӿ���weibo4j.Weibo���ж��壬���÷�ʽ��ο�weibo4j.examples�������������Ľӿ��б�

��ȡ�������ݼ�(timeline)�ӿ�  ��������
statuses/public_timeline ���¹���΢��      ��������getPublicTimeline
statuses/friends_timeline ���¹�ע��΢�� (����: statuses/home_timeline)  ��������getFriendsTimeline 
statuses/user_timeline �û�����΢���б�   ��������getUserTimeline(String id, Paging paging)
statuses/mentions ���� @�û���    �������� getMentions()
statuses/comments ���������б�(��΢��)   ��������getComments()
statuses/repost_timeline ����һ��ԭ��΢��������n��ת��΢����Ϣ
statuses/repost_by_me �����û�ת��������n��΢����Ϣ
users/hot ��ȡϵͳ�Ƽ��û� 


΢�����ʽӿ�
statuses/show ��ȡ����   ��������showStatus(long id)

statuses/update ����΢��   ��������updateStatus(String status)
statuses/upload ����΢����ͼƬ ��������uploadStatus(String status,File file)
statuses/destroy ɾ�� �������� destroyStatus(long statusId)

statuses/comment ���� �������� destroyComment(long commentId)
statuses/comment_destroy ɾ������  ��������destroyComment
Statuses/comment/destroy batch ����ɾ����ǰ�û���΢��������Ϣ 
statuses/reset_count δ����Ϣ������ӿ� 

˽�Žӿ�
direct_messages �ҵ�˽���б� ��������getDirectMessages() ��ҳ getDirectMessages(Paging paging)
direct_messages/sent �ҷ��͵�˽���б� ��������getSentDirectMessages()
direct_messages/new ����˽�� ��������sendDirectMessage(String id,String text)
direct_messages/destroy ɾ��һ��˽�� ��������destroyDirectMessage(int id)

��ע�ӿ�
friendships/create ��עĳ�û� ��������createFriendship(String id)�� createFriendship(String id, boolean follow)
friendships/destroy ȡ����ע �������� destroyFriendship(String id)
friendships/exists �Ƿ��עĳ�û� ��������existsFriendship(String userA, String userB)

friends/ids ��ע�б� �������� getFriendsIDs(long cursor)
followers/ids ��˿�б� ��������getFollowersIDs(long cursor)

�˺Žӿ�
account/verify_credentials ��֤����Ƿ�Ϸ� ��������verifyCredentials()
account/rate_limit_status �鿴��ǰƵ������ ��������rateLimitStatus()

account/update_profile_image ����ͷ�� ��������updateProfileImage(File image)
account/update_profile �������� ��������User updateProfile(String name, String email, String url, String location, String 

description)

�ղؽӿ�
favorites �ղ��б� ��������getFavorites()
favorites/create ����ղ� ��������createFavorite(long id)
favorites/destroy ɾ���ղ� ��������destroyFavorite(long id)

����ɾ���ӿ�

direct_messages/destroy_batch ����ɾ��˽�� 
favorites/destroy_batch ����ɾ���ղص�΢����Ϣ 

�û���ǩ�ӿ�
tags ����ָ���û��ı�ǩ�б� 
tags/create ����û���ǩ 
tags/suggestions �����û�����Ȥ�ı�ǩ 
tags/destroy ɾ����ǩ 
tags/destroy_batch ����ɾ����ǩ 


