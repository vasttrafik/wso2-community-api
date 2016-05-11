--
-- Drop all constraints
--
ALTER TABLE COM_ATTACHMENT
DROP CONSTRAINT FK_ATTACHMENT_MESSAGE;
	
ALTER TABLE COM_FOLDER
DROP CONSTRAINT FK_FOLDER_MEMBER;
	
ALTER TABLE COM_FOLDER_MESSAGE
DROP CONSTRAINT FK_MESSAGE_FOLDER;
	
ALTER TABLE COM_FOLDER_MESSAGE
DROP CONSTRAINT FK_FOLDER_MESSAGE;
	
ALTER TABLE COM_FORUM
DROP CONSTRAINT FK_FORUM_CATEGORY;
	
ALTER TABLE COM_FORUM_WATCH
DROP CONSTRAINT FK_FORUM_WATCH;
	
ALTER TABLE COM_FORUM_WATCH
DROP CONSTRAINT FK_FORUM_WATCH_MEMBER;
	
ALTER TABLE COM_MEMBER_RANKING
DROP CONSTRAINT FK_RANKING_MEMBER;
	
ALTER TABLE COM_MEMBER_RANKING
DROP CONSTRAINT FK_MEMBER_RANKING;
	
--ALTER TABLE COM_MESSAGE
--DROP CONSTRAINT FK_MESSAGE_ORIGINAL;
	
ALTER TABLE COM_MESSAGE
DROP CONSTRAINT FK_MESSAGE_SENDER;
		
ALTER TABLE COM_POST
DROP CONSTRAINT FK_POST_FORUM;
	
ALTER TABLE COM_POST
DROP CONSTRAINT FK_POST_TOPIC;
	
ALTER TABLE COM_POST
DROP CONSTRAINT FK_POST_SUBMITTED_BY;

ALTER TABLE COM_POST
DROP CONSTRAINT FK_POST_EDITED_BY;
	
ALTER TABLE COM_POST_EDIT
DROP CONSTRAINT FK_POST_EDIT;

ALTER TABLE COM_POST_EDIT
DROP CONSTRAINT FK_POST_EDIT_CREATED_BY;

ALTER TABLE COM_REPORT
DROP CONSTRAINT FK_REPORT_POST;
	
ALTER TABLE COM_REPORT
DROP CONSTRAINT FK_REPORT_REPORTED_BY;
	
ALTER TABLE COM_REPORT_MEASURE
DROP CONSTRAINT FK_REPORT_MEASURE_REPORT;	
	
--ALTER TABLE COM_REPORT_MEASURE
--DROP CONSTRAINT FK_REPORT_MEASURE_MESSAGE;	

ALTER TABLE COM_REPORT_MEASURE
DROP CONSTRAINT FK_REPORT_MEASURE_RECTIFIED_BY;	
	
ALTER TABLE COM_REPORT_MEASURE
DROP CONSTRAINT FK_REPORT_MEASURE_NOTIFICATION;

ALTER TABLE COM_TOPIC
DROP CONSTRAINT FK_TOPIC_FORUM;
	
ALTER TABLE COM_TOPIC
DROP CONSTRAINT FK_TOPIC_MEMBER;
	
ALTER TABLE COM_TOPIC
DROP CONSTRAINT FK_TOPIC_CLOSED_BY;

ALTER TABLE COM_TOPIC_TAG
DROP CONSTRAINT FK_TOPIC_TAG;

ALTER TABLE COM_TOPIC_TAG
DROP CONSTRAINT FK_TOPIC_TAG_TAG;

ALTER TABLE COM_TOPIC_WATCH
DROP CONSTRAINT FK_TOPIC_WATCH;
	
ALTER TABLE COM_TOPIC_WATCH
DROP CONSTRAINT FK_TOPIC_WATCH_MEMBER;
	
ALTER TABLE COM_VOTE
DROP CONSTRAINT FK_VOTE_POST;
		
ALTER TABLE COM_VOTE
DROP CONSTRAINT FK_VOTE_MEMBER;

--
-- Drop all views
--
DROP VIEW COM_POST_VIEW;
DROP VIEW COM_WATCH_VIEW;
DROP VIEW COM_TOPIC_LAST_POST_VIEW;
DROP VIEW COM_TOPIC_LABEL_POPULAR_VIEW;
DROP VIEW COM_TOPIC_LABEL_ANSWERED_VIEW;
DROP VIEW COM_TOPIC_LABEL_UNANSWERED_VIEW;

--
-- Drop all triggers
--
DROP TRIGGER ATTACHMENTTRIGGER;
DROP TRIGGER DELETEFOLDERTRIGGER;
DROP TRIGGER DELETEMEMBERTRIGGER;
DROP TRIGGER DELETEPOSTTRIGGER;
DROP TRIGGER DELETETOPICTRIGGER;
DROP TRIGGER FORUMTRIGGER;
DROP TRIGGER INSERTPOSTTRIGGER;
DROP TRIGGER INSERTTOPICTRIGGER;
DROP TRIGGER INSERTREPORTTRIGGER;
DROP TRIGGER MESSAGETRIGGER;
DROP TRIGGER UPDATEPOSTTRIGGER;
DROP TRIGGER VOTETRIGGER;

--
-- Drop all tables
--
DROP TABLE COM_ATTACHMENT;
DROP TABLE COM_CATEGORY;
DROP TABLE COM_CONFIG;
DROP TABLE COM_FOLDER;
DROP TABLE COM_FOLDER_MESSAGE;
DROP TABLE COM_FORUM;
DROP TABLE COM_FORUM_WATCH;
DROP TABLE COM_MEMBER;
DROP TABLE COM_MEMBER_RANKING;
DROP TABLE COM_MESSAGE;
DROP TABLE COM_POST;
DROP TABLE COM_POST_EDIT;
DROP TABLE COM_RANKING;
DROP TABLE COM_REPORT;
DROP TABLE COM_REPORT_MEASURE;
DROP TABLE COM_TAG;
DROP TABLE COM_TOPIC;
DROP TABLE COM_TOPIC_TAG;
DROP TABLE COM_TOPIC_WATCH;
DROP TABLE COM_VOTE;