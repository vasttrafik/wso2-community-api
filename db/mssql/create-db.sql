--
-- Table structure for table 'COM_ATTACHMENT'
--
-- NOTE: Attachments are allowed in private messages, not in forums
--
CREATE TABLE COM_ATTACHMENT (
	COM_ID             INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
	COM_MESSAGE_ID     INT NOT NULL,
	COM_FILE_NAME      VARCHAR(255) NOT NULL,
	COM_CONTENT        VARBINARY(MAX) NOT NULL,
	COM_MIME_TYPE      VARCHAR(255),
	COM_SIZE           INT
);

CREATE INDEX IX_ATTACHMENT_MESSAGE ON COM_ATTACHMENT (COM_MESSAGE_ID);

--
-- Table structure for table 'COM_CATEGORY'
--
-- NOTE: Category images are stored in the file system for efficient access
--
CREATE TABLE COM_CATEGORY (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_NAME             VARCHAR(100) NOT NULL,
  COM_IS_PUBLIC        BIT NOT NULL DEFAULT 1,
  COM_IMAGE_URL        VARCHAR(255),
  COM_NUM_FORUMS       SMALLINT DEFAULT 0
);

--
-- Table structure for table 'COM_CONFIG'
--
CREATE TABLE COM_CONFIG (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_NAME             VARCHAR(255) NOT NULL,
  COM_VALUE            VARCHAR(255) NOT NULL
);

--
-- Table structure for table 'COM_FOLDER'
--
-- NOTE: Folder images are stored in the file system for efficient access
--       Folder type information is stored in the com_type column and valid
--       values are (the first 4 are system folders that can't be moved,
--       renamed or deleted):
--         * inbox
--         * sent
--         * drafts
--         * trash
--         * user (user-defined folders)
--
CREATE TABLE COM_FOLDER (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_PARENT_ID        INT,
  COM_MEMBER_ID        INT NOT NULL,
  COM_NAME             VARCHAR(255) NOT NULL,
  COM_TYPE             VARCHAR(12) NOT NULL,
  COM_IMAGE_URL        VARCHAR(255) NOT NULL,
  COM_NUM_MSGS         SMALLINT DEFAULT 0,
  COM_NUM_UNREAD_MSGS  SMALLINT DEFAULT 0,
  COM_SIZE             INT DEFAULT 0
);

CREATE INDEX IX_FOLDER_PARENT ON COM_FOLDER (COM_PARENT_ID);
CREATE INDEX IX_FOLDER_MEMBER ON COM_FOLDER (COM_MEMBER_ID);

--
-- Table structure for table 'COM_FOLDER_MESSAGE'
--
CREATE TABLE COM_FOLDER_MESSAGE (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_FOLDER_ID        INT NOT NULL,
  COM_MESSAGE_ID       INT NOT NULL,
  COM_READ_DATE        DATETIME DEFAULT NULL
);

CREATE UNIQUE INDEX IX_FOLDER_MESSAGE ON COM_FOLDER_MESSAGE (COM_FOLDER_ID, COM_MESSAGE_ID);

--
-- Table structure for table 'COM_FORUM'
--
-- NOTE: Forum images are stored in the file system for efficient access
--
-- TO-DO: Handle case where forum is moved to a different category?
--
CREATE TABLE COM_FORUM (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_CATEGORY_ID      INT NOT NULL,
  COM_NAME             VARCHAR(255) NOT NULL,
  COM_DESC             VARCHAR(255) NOT NULL,
  COM_IMAGE_URL        VARCHAR(255),
  COM_NUM_TOPICS       INT DEFAULT 0,
  COM_NUM_POSTS        INT DEFAULT 0,
  COM_LAST_POST_ID     INT DEFAULT NULL
);

--CREATE INDEX IX_FORUM_NAME ON COM_FORUM (COM_NAME);

--
-- Table structure for table 'COM_FORUM_WATCH'
--
CREATE TABLE COM_FORUM_WATCH (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_FORUM_ID         INT NOT NULL,
  COM_MEMBER_ID        INT NOT NULL
);

--
-- Table structure for table 'COM_MEMBER'
--
-- Note: The status of the member (COM_STATUS) can be: 
--       inactive (community feature not activated)
--       active
--       probation (member has been reported)
--       banned (due to multiple reports, the member is banned from the forums)
--       deleted
CREATE TABLE COM_MEMBER (
  COM_ID               INT NOT NULL PRIMARY KEY,
  COM_USER_NAME        VARCHAR(255) NOT NULL,
  COM_EMAIL            VARCHAR(255) NOT NULL,            
  COM_STATUS           VARCHAR(20) NOT NULL DEFAULT 'inactive',	
  COM_SHOW_EMAIL       BIT DEFAULT 0,       			-- Privacy settings. If email should be displayed in forums
  COM_SHOW_RANKINGS    BIT DEFAULT 0,       			-- -"-. If rankings information should be displayed in forums
  COM_SIGNATURE        VARCHAR(64),             		-- -"-. Signature
  COM_USE_GRAVATAR     BIT DEFAULT 0,       			-- -"-. If gravatar should be used in forums
  COM_GRAVATAR_EMAIL   VARCHAR(255),            		-- -"-. Gravatar email address
  COM_ACCEPT_ALL_MSG   BIT DEFAULT 0,       			-- -"-. If messages should be accepted from all users
  COM_NOTIFY_EMAIL     BIT DEFAULT 1,       			-- Notification settings. If notifications should be sent by email
  COM_NOTIFY_MESSAGE   BIT DEFAULT 0,       			-- -"-. If notifications should be internally managed
  COM_NOTIFY_TEXT      BIT DEFAULT 0,       			-- -"-. If notifications should include the text of posts
);

--
-- Table structure for table 'COM_MEMBER_RANKING'
--
CREATE TABLE COM_MEMBER_RANKING (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_MEMBER_ID        INT NOT NULL,
  COM_RANKING_ID       INT NOT NULL,
  COM_CURRENT_SCORE    INT DEFAULT 1
);

CREATE UNIQUE INDEX IX_USER_RANKING ON COM_MEMBER_RANKING (COM_MEMBER_ID, COM_RANKING_ID);

--
-- Table structure for table 'COM_MESSAGE'
--
CREATE TABLE COM_MESSAGE (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_ORIGINAL_ID      INT DEFAULT NULL,
  COM_SUBJECT          VARCHAR(255) NOT NULL,
  COM_BODY             NVARCHAR(MAX) NOT NULL,
  COM_SENDER_ID        INT NOT NULL,
  COM_CREATED_DATE     DATETIME DEFAULT CURRENT_TIMESTAMP,
  COM_ENABLE_HTML      BIT DEFAULT 0,
  COM_NUM_ATTACHMENTS  SMALLINT DEFAULT 0
);

--
-- Table structure for table 'COM_POST'
--
-- NOTE: When someone comments on questions or answers, the COM_COMMENT_TO is being used
--       When new versions are being created, this table will hold the most recent version
--       Valid values for the COM_TYPE column are 'question', 'answer', 'comment'
--       Valid values for the COM_TEXT_FORMAT column are 'md', 'html' and 'plain'
--
CREATE TABLE COM_POST (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_TOPIC_ID         INT NOT NULL,
  COM_FORUM_ID         INT NOT NULL,
  COM_TYPE             VARCHAR(12) NOT NULL DEFAULT 'question',
  COM_TEXT             NVARCHAR(MAX) NOT NULL,
  COM_TEXT_FORMAT      VARCHAR(5) NOT NULL DEFAULT 'md',
  COM_CREATED_DATE     DATETIME DEFAULT CURRENT_TIMESTAMP,
  COM_CREATED_BY_ID    INT NOT NULL,
  COM_COMMENT_TO_ID    INT DEFAULT NULL,
  COM_POINTS_AWARDED   SMALLINT DEFAULT 0,
  COM_IS_ANSWER        BIT DEFAULT 0,
  COM_EDIT_DATE        DATETIME DEFAULT NULL,
  COM_EDITED_BY_ID     INT DEFAULT NULL,
  COM_EDIT_COUNT       SMALLINT DEFAULT 0,
  COM_IS_MODERATED     BIT DEFAULT 0,
  COM_IS_DELETED       BIT DEFAULT 0,
  COM_IS_REPORTED      BIT DEFAULT 0
);

CREATE INDEX IX_POST_TOPIC ON COM_POST (COM_TOPIC_ID);
CREATE INDEX IX_POST_FORUM ON COM_POST (COM_FORUM_ID);
CREATE INDEX IX_POST_CREATED ON COM_POST (COM_CREATED_BY_ID);
CREATE INDEX IX_POST_COMMENT ON COM_POST (COM_COMMENT_TO_ID);
CREATE INDEX IX_POST_EDITED ON COM_POST (COM_EDITED_BY_ID);

--
-- Table structure for table 'COM_POST_EDIT'
--
-- NOTE: Used to keep previous versions of posts
--       New versions are created when
--
--       1. Question is edited
--       2. Answer is edited
--       3. Comment is edited
--       4. Question, answer or comment is moderated
--
CREATE TABLE COM_POST_EDIT (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_POST_ID          INT NOT NULL,
  COM_EDIT_VERSION     SMALLINT DEFAULT 1,
  COM_TEXT             NVARCHAR(MAX) NOT NULL,
  COM_TEXT_FORMAT      VARCHAR(5) NOT NULL,
  COM_CREATED_DATE     DATETIME DEFAULT CURRENT_TIMESTAMP,
  COM_CREATED_BY_ID    INT NOT NULL
);

CREATE INDEX IX_POST_EDIT ON COM_POST_EDIT (COM_POST_ID);
CREATE INDEX IX_POST_EDIT_USER ON COM_POST_EDIT (COM_CREATED_BY_ID);

--
-- Table structure for table 'COM_RANKING'
--
-- NOTE: Ranking images are stored in the file system
--
CREATE TABLE COM_RANKING (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_TITLE            VARCHAR(50) NOT NULL,
  COM_TYPE             VARCHAR(10) NOT NULL,
  COM_MIN_POINTS       INT NOT NULL DEFAULT 0,
  COM_IMAGE_URL        VARCHAR(255) DEFAULT NULL
);

--
-- Table structure for table 'COM_REPORT'
--
CREATE TABLE COM_REPORT (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_POST_ID          INT NOT NULL,
  COM_TYPE             VARCHAR(10) NOT NULL,
  COM_TEXT             NVARCHAR(4000) NOT NULL,
  COM_REPORT_DATE      DATETIME DEFAULT GETDATE(),
  COM_REPORTED_BY_ID   INT NOT NULL,
);

CREATE INDEX IX_REPORT_POST ON COM_POST_EDIT (COM_POST_ID);

--
-- Table structure for table 'COM_MEASURE'
--
CREATE TABLE COM_REPORT_MEASURE (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_REPORT_ID        INT NOT NULL,
  COM_TYPE             VARCHAR(12) NOT NULL,
  COM_MESSAGE_ID       INT,
  COM_MEASURE_DATE     DATETIME DEFAULT GETDATE(),
  COM_RECTIFIED_BY_ID  INT NOT NULL,
  COM_NOTIFICATION_ID  INT
);

CREATE UNIQUE INDEX IX_REPORT_MEASURE ON COM_REPORT_MEASURE (COM_ID, COM_REPORT_ID);

--
-- Table structure for table 'COM_TAG'
--
CREATE TABLE COM_TAG (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_LABEL            VARCHAR(100) NOT NULL
);

--
-- Table structure for table 'COM_TOPIC'
--
CREATE TABLE COM_TOPIC (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_FORUM_ID         INT NOT NULL,
  COM_SUBJECT          VARCHAR(200) NOT NULL,
  COM_CREATED_DATE     DATETIME DEFAULT CURRENT_TIMESTAMP,
  COM_CREATED_BY_ID    INT NOT NULL,
  COM_CLOSED_DATE      DATETIME DEFAULT NULL,
  COM_CLOSED_BY_ID     INT DEFAULT NULL,
  COM_NUM_POSTS        SMALLINT DEFAULT 0,
  COM_NUM_VIEWS        SMALLINT DEFAULT 0,
  COM_NUM_ANSWERS      SMALLINT DEFAULT 0,
  COM_FIRST_POST_ID    INT DEFAULT NULL,
  COM_LAST_POST_ID     INT DEFAULT NULL,
  COM_ANSWER_POST_ID   INT DEFAULT NULL,
  COM_IS_DELETED       BIT DEFAULT 0
);

CREATE INDEX IX_TOPIC_FORUM ON COM_TOPIC (COM_FORUM_ID);

--
-- Table structure for table 'COM_TOPIC_TAG'
--
CREATE TABLE COM_TOPIC_TAG (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_TOPIC_ID         INT NOT NULL,
  COM_TAG_ID           INT NOT NULL
);

--
-- Table structure for table 'COM_TOPIC_WATCH'
--
CREATE TABLE COM_TOPIC_WATCH (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_TOPIC_ID         INT NOT NULL,
  COM_MEMBER_ID        INT NOT NULL
);

CREATE UNIQUE INDEX IX_TOPIC_WATCH ON COM_TOPIC_WATCH (COM_TOPIC_ID, COM_MEMBER_ID);

--
-- Table structure for table 'COM_VOTE'
--
-- NOTE: Valid values for COM_TYPE are question, comment, answer
--
CREATE TABLE COM_VOTE (
  COM_ID               INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  COM_POST_ID          INT NOT NULL,
  COM_TYPE             VARCHAR(10) DEFAULT 'ANSWER',
  COM_MEMBER_ID        INT NOT NULL,
  COM_POINTS           SMALLINT NOT NULL
);

CREATE UNIQUE INDEX IX_VOTE_POST ON COM_VOTE (COM_POST_ID);

--
-- SELECT statement for view COM_POST_VIEW
--
CREATE VIEW COM_POST_VIEW AS
  SELECT a.COM_ID, 
         a.COM_TOPIC_ID, 
		 a.COM_FORUM_ID, 
		 c.COM_CATEGORY_ID, 
         a.COM_TYPE, 
		 a.COM_TEXT, 
		 a.COM_TEXT_FORMAT, 
		 a.COM_CREATED_BY_ID, 
         a.COM_CREATED_DATE, 
		 a.COM_COMMENT_TO_ID, 
		 a.COM_POINTS_AWARDED, 
         a.COM_IS_ANSWER, 
		 a.COM_EDIT_DATE, 
		 a.COM_EDITED_BY_ID, 
		 a.COM_EDIT_COUNT, 
         a.COM_IS_MODERATED, 
		 a.COM_IS_DELETED, 
		 a.COM_IS_REPORTED, 
         ORDERING = CASE 
		   WHEN A.COM_COMMENT_TO_ID IS NULL THEN A.COM_ID 
		   WHEN A.COM_COMMENT_TO_ID IS NOT NULL THEN A.COM_COMMENT_TO_ID 
		 END
   FROM COM_POST  a, 
        COM_TOPIC b, 
		COM_FORUM c 
  WHERE a.COM_TOPIC_ID = b.COM_ID AND b.COM_FORUM_ID = c.COM_ID;

CREATE VIEW COM_WATCH_VIEW AS
  SELECT a.COM_ID, 
         a.COM_FORUM_ID, 
		 NULL AS COM_TOPIC_ID, 
		 a.COM_MEMBER_ID, 
		 b.COM_NAME AS COM_TITLE 
   FROM COM_FORUM_WATCH a, 
        COM_FORUM b
  WHERE a.COM_FORUM_ID = b.COM_ID
  UNION ALL
  SELECT a.COM_ID, 
         NULL AS COM_FORUM_ID, 
		 a.COM_TOPIC_ID, 
		 a.COM_MEMBER_ID, 
		 b.COM_SUBJECT AS COM_TITLE 
    FROM COM_TOPIC_WATCH a, 
	     COM_TOPIC b
   WHERE a.COM_TOPIC_ID = b.COM_ID;

--
-- Add foreign key constraints
--
ALTER TABLE COM_ATTACHMENT
ADD CONSTRAINT FK_ATTACHMENT_MESSAGE FOREIGN KEY (COM_MESSAGE_ID) 
    REFERENCES COM_MESSAGE (COM_ID) 
    ON DELETE CASCADE;
	
-- If user is marked as deleted, manually cascade
ALTER TABLE COM_FOLDER
ADD CONSTRAINT FK_FOLDER_MEMBER FOREIGN KEY (COM_MEMBER_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
-- When a folder is deleted, cascade to message references
ALTER TABLE COM_FOLDER_MESSAGE
ADD CONSTRAINT FK_MESSAGE_FOLDER FOREIGN KEY (COM_FOLDER_ID) 
    REFERENCES COM_FOLDER (COM_ID)
	ON DELETE CASCADE;
	
ALTER TABLE COM_FOLDER_MESSAGE
ADD CONSTRAINT FK_FOLDER_MESSAGE FOREIGN KEY (COM_MESSAGE_ID) 
    REFERENCES COM_MESSAGE (COM_ID)
	ON DELETE CASCADE;
	
ALTER TABLE COM_FORUM
ADD CONSTRAINT FK_FORUM_CATEGORY FOREIGN KEY (COM_CATEGORY_ID) 
    REFERENCES COM_CATEGORY (COM_ID) 
    ON DELETE CASCADE;
	
ALTER TABLE COM_FORUM_WATCH
ADD CONSTRAINT FK_FORUM_WATCH FOREIGN KEY (COM_FORUM_ID) 
    REFERENCES COM_FORUM (COM_ID) 
    ON DELETE CASCADE;
	
-- If user is marked as deleted, manually cascade
ALTER TABLE COM_FORUM_WATCH
ADD CONSTRAINT FK_FORUM_WATCH_MEMBER FOREIGN KEY (COM_MEMBER_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
-- If user is marked as deleted, manually cascade
ALTER TABLE COM_MEMBER_RANKING
ADD CONSTRAINT FK_RANKING_MEMBER FOREIGN KEY (COM_MEMBER_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
ALTER TABLE COM_MEMBER_RANKING
ADD CONSTRAINT FK_MEMBER_RANKING FOREIGN KEY (COM_RANKING_ID) 
    REFERENCES COM_RANKING (COM_ID);
	
--SQL Error: Introducing FOREIGN KEY constraint 'FK_MESSAGE_ORIGINAL' on table 'COM_MESSAGE' may cause cycles or multiple cascade paths
--ALTER TABLE COM_MESSAGE
--ADD CONSTRAINT FK_MESSAGE_ORIGINAL FOREIGN KEY (COM_ORIGINAL_ID) 
    --REFERENCES COM_MESSAGE (COM_ID) 
	--ON DELETE CASCADE;
	
ALTER TABLE COM_MESSAGE
ADD CONSTRAINT FK_MESSAGE_SENDER FOREIGN KEY (COM_SENDER_ID) 
    REFERENCES COM_MEMBER (COM_ID);
		
-- No need to cascade, it will cascade from topic
ALTER TABLE COM_POST
ADD CONSTRAINT FK_POST_FORUM FOREIGN KEY (COM_FORUM_ID) 
    REFERENCES COM_FORUM (COM_ID);
	
ALTER TABLE COM_POST
ADD CONSTRAINT FK_POST_TOPIC FOREIGN KEY (COM_TOPIC_ID) 
    REFERENCES COM_TOPIC (COM_ID);
	
ALTER TABLE COM_POST
ADD CONSTRAINT FK_POST_SUBMITTED_BY FOREIGN KEY (COM_CREATED_BY_ID) 
    REFERENCES COM_MEMBER (COM_ID);

ALTER TABLE COM_POST
ADD CONSTRAINT FK_POST_EDITED_BY FOREIGN KEY (COM_EDITED_BY_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
ALTER TABLE COM_POST_EDIT
ADD CONSTRAINT FK_POST_EDIT FOREIGN KEY (COM_POST_ID) 
    REFERENCES COM_POST (COM_ID);

ALTER TABLE COM_POST_EDIT
ADD CONSTRAINT FK_POST_EDIT_CREATED_BY FOREIGN KEY (COM_CREATED_BY_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
ALTER TABLE COM_REPORT
ADD CONSTRAINT FK_REPORT_POST FOREIGN KEY (COM_POST_ID) 
    REFERENCES COM_POST (COM_ID)
	ON DELETE CASCADE;
	
ALTER TABLE COM_REPORT
ADD CONSTRAINT FK_REPORT_REPORTED_BY FOREIGN KEY (COM_REPORTED_BY_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
ALTER TABLE COM_REPORT_MEASURE
ADD CONSTRAINT FK_REPORT_MEASURE_REPORT FOREIGN KEY (COM_REPORT_ID) 
    REFERENCES COM_REPORT (COM_ID)
	ON DELETE CASCADE;	
	
--SQL Error: Introducing FOREIGN KEY constraint 'FK_REPORT_MEASURE_MESSAGE' on table 'COM_REPORT_MEASURE' may cause cycles or multiple cascade paths
--ALTER TABLE COM_REPORT_MEASURE
--ADD CONSTRAINT FK_REPORT_MEASURE_MESSAGE FOREIGN KEY (COM_MESSAGE_ID) 
    --REFERENCES COM_MESSAGE (COM_ID)
	--ON DELETE SET NULL;	

ALTER TABLE COM_REPORT_MEASURE
ADD CONSTRAINT FK_REPORT_MEASURE_RECTIFIED_BY FOREIGN KEY (COM_RECTIFIED_BY_ID) 
    REFERENCES COM_MEMBER (COM_ID);	

ALTER TABLE COM_REPORT_MEASURE
ADD CONSTRAINT FK_REPORT_MEASURE_NOTIFICATION FOREIGN KEY (COM_NOTIFICATION_ID) 
    REFERENCES COM_MESSAGE (COM_ID)
	ON DELETE SET NULL;

ALTER TABLE COM_TOPIC
ADD CONSTRAINT FK_TOPIC_FORUM FOREIGN KEY (COM_FORUM_ID) 
    REFERENCES COM_FORUM (COM_ID);
	
ALTER TABLE COM_TOPIC
ADD CONSTRAINT FK_TOPIC_MEMBER FOREIGN KEY (COM_CREATED_BY_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
ALTER TABLE COM_TOPIC
ADD CONSTRAINT FK_TOPIC_CLOSED_BY FOREIGN KEY (COM_CLOSED_BY_ID) 
    REFERENCES COM_MEMBER (COM_ID);

ALTER TABLE COM_TOPIC_WATCH
ADD CONSTRAINT FK_TOPIC_WATCH FOREIGN KEY (COM_TOPIC_ID) 
    REFERENCES COM_TOPIC (COM_ID) 
    ON DELETE CASCADE;
	
-- If user is marked as deleted, manually cascade
ALTER TABLE COM_TOPIC_WATCH
ADD CONSTRAINT FK_TOPIC_WATCH_MEMBER FOREIGN KEY (COM_MEMBER_ID) 
    REFERENCES COM_MEMBER (COM_ID);
	
ALTER TABLE COM_TOPIC_TAG
ADD CONSTRAINT FK_TOPIC_TAG FOREIGN KEY (COM_TOPIC_ID) 
    REFERENCES COM_TOPIC (COM_ID) 
    ON DELETE CASCADE;
	
ALTER TABLE COM_TOPIC_TAG
ADD CONSTRAINT FK_TOPIC_TAG_TAG FOREIGN KEY (COM_TAG_ID) 
    REFERENCES COM_TAG (COM_ID)
	ON DELETE CASCADE;
	
ALTER TABLE COM_VOTE
ADD CONSTRAINT FK_VOTE_POST FOREIGN KEY (COM_POST_ID) 
    REFERENCES COM_POST (COM_ID) 
    ON DELETE CASCADE;
		
ALTER TABLE COM_VOTE
ADD CONSTRAINT FK_VOTE_MEMBER FOREIGN KEY (COM_MEMBER_ID) 
    REFERENCES COM_MEMBER (COM_ID);

--
-- Create trigger ATTACHMENTTRIGGER
--
-- NOTE: Updates the COM_SIZE column in 
--       the COM_FOLDER table   
--
CREATE TRIGGER ATTACHMENTTRIGGER ON DBO.COM_ATTACHMENT
  AFTER INSERT, DELETE
AS 

  DECLARE @FolderId INT, @Size INT;
  DECLARE @Attachment CURSOR;
  
  IF EXISTS (SELECT * FROM INSERTED)
    BEGIN
	
	  SET @Attachment = CURSOR FOR
        SELECT B.COM_FOLDER_ID, A.COM_SIZE 
	      FROM INSERTED A,
	           DBO.COM_FOLDER_MESSAGE B
         WHERE A.COM_MESSAGE_ID = B.COM_MESSAGE_ID;
	
	  OPEN @Attachment;
      FETCH NEXT FROM @Attachment 
        INTO @FolderId, @Size;
      CLOSE @Attachment;
      DEALLOCATE @Attachment;		
	  
	  -- Update the size of the folder
	  UPDATE DBO.COM_FOLDER
	     SET COM_SIZE = COM_SIZE + @Size
	   WHERE COM_ID = @FolderId;
	   
    END;
  ELSE IF EXISTS(SELECT * FROM DELETED)
    BEGIN
	
	  SET @Attachment = CURSOR FOR
        SELECT B.COM_FOLDER_ID, A.COM_SIZE 
	      FROM DELETED A,
	           DBO.COM_FOLDER_MESSAGE B
         WHERE A.COM_MESSAGE_ID = B.COM_MESSAGE_ID;
	
	  OPEN @Attachment;
      FETCH NEXT FROM @Attachment 
        INTO @FolderId, @Size;
      CLOSE @Attachment;
      DEALLOCATE @Attachment;		
	  
	  -- Update the size of the folder
	  UPDATE DBO.COM_FOLDER
	     SET COM_SIZE = COM_SIZE - @Size
	   WHERE COM_ID = @FolderId;
	   
    END;
	
--
-- Create trigger FORUMTRIGGER
--
-- NOTE: Updates COM_NUM_FORUMS column in COM_CATEGORY table
--        
--
CREATE TRIGGER FORUMTRIGGER ON DBO.COM_FORUM
  AFTER INSERT, DELETE
AS 

  IF EXISTS (SELECT * FROM INSERTED)
    BEGIN
	
	  -- Update the number of forums in the category
	  UPDATE COM_CATEGORY
	     SET COM_NUM_FORUMS = COM_NUM_FORUMS + 1
	   WHERE COM_ID = (
	     SELECT COM_CATEGORY_ID
		 FROM INSERTED
	   );
	   
    END;
  ELSE IF EXISTS(SELECT * FROM DELETED)
    BEGIN
	
	  -- Update the number of forums in the category
	  UPDATE COM_CATEGORY
	     SET COM_NUM_FORUMS = COM_NUM_FORUMS - 1
	   WHERE COM_ID = (
	     SELECT COM_CATEGORY_ID
		 FROM DELETED
	   );
	   
    END;
  
--
-- Create trigger DELETEFOLDERTRIGGER
--
-- NOTE: Deletes a folder recursively
--
CREATE TRIGGER DELETEFOLDERTRIGGER ON DBO.COM_FOLDER
  INSTEAD OF DELETE
AS 
BEGIN

  DECLARE @FolderId INT;
  DECLARE @Folder CURSOR;
  
  -- Recursive cursor to get a folder including all the sub folders
  SET @Folder = CURSOR FOR
  WITH T AS(
    SELECT COM_ID, COM_PARENT_ID
	  FROM DBO.COM_FOLDER
	  WHERE COM_ID = (
        SELECT COM_ID
          FROM DELETED
      )
	UNION ALL
    SELECT C.COM_ID, C.COM_PARENT_ID
	  FROM T P JOIN DBO.COM_FOLDER C ON C.COM_PARENT_ID = P.COM_ID
  )
  SELECT COM_ID FROM T;
  
  OPEN @Folder;
  
  FETCH NEXT FROM @Folder 
  INTO @FolderId;

  WHILE @@FETCH_STATUS = 0
  BEGIN
  
    -- Delete folder
    DELETE FROM DBO.COM_FOLDER
	  WHERE COM_ID = @FolderId;
	  
	FETCH NEXT FROM @Folder 
    INTO @FolderId;
	  
  END;
  
  CLOSE @Folder;
  DEALLOCATE @Folder;
  
  -- When a folder is deleted, message references in COM_FOLDER_MESSAGE
  -- are cascaded by foreign constraint rule. When we are done, we need 
  -- to check that messages without references are left in COM_MESSAGE. 
  DELETE FROM COM_MESSAGE
    WHERE NOT EXISTS(
	  SELECT DISTINCT COM_MESSAGE_ID
	    FROM COM_FOLDER_MESSAGE
	);
	
END;

--
-- Create trigger DELETEMEMBERTRIGGER
--
-- NOTE: Sets deleted flag on COM_MEMBER
--        Deletes records in associated tables
--
CREATE TRIGGER DELETEMEMBERTRIGGER ON DBO.COM_MEMBER
  INSTEAD OF DELETE
AS 
  BEGIN

    -- Set the flag to indicate that the member has been deleted
    UPDATE DBO.COM_MEMBER 
       SET COM_STATUS = 'deleted'
     WHERE COM_ID = (
       SELECT COM_ID
         FROM DELETED
     );

	-- Delete the rankings of the member
    DELETE 
	  FROM DBO.COM_MEMBER_RANKING 
     WHERE COM_MEMBER_ID = (
       SELECT COM_ID
         FROM DELETED
     );
  
    -- Delete all topic watches of the member
    DELETE 
	  FROM DBO.COM_TOPIC_WATCH 
     WHERE COM_MEMBER_ID = (
       SELECT COM_ID
         FROM DELETED
     );
  
    -- Delete all forum watches of the member
    DELETE 
	  FROM DBO.COM_FORUM_WATCH 
     WHERE COM_MEMBER_ID = (
       SELECT COM_ID
         FROM DELETED
     );
  
    DELETE 
	  FROM DBO.COM_FOLDER 
     WHERE COM_MEMBER_ID = (
       SELECT COM_ID
         FROM DELETED
     );

  END;

--
-- Create trigger DELETEPOSTTRIGGER
--
CREATE TRIGGER DELETEPOSTTRIGGER ON DBO.COM_POST
  INSTEAD OF DELETE
AS
  BEGIN
  
    -- Set flag to indicate that the post has been deleted
    UPDATE DBO.COM_POST
       SET COM_IS_DELETED = 1
     WHERE COM_ID = (
       SELECT COM_ID
         FROM DELETED
     );
  
    -- Update number of posts in the forum
    UPDATE DBO.COM_FORUM
	   SET COM_NUM_POSTS = COM_NUM_POSTS - 1
	 WHERE COM_ID = (
	   SELECT COM_FORUM_ID
		 FROM DELETED
	 );
	   
	-- Update number of posts in the topic
	UPDATE DBO.COM_TOPIC
	   SET COM_NUM_POSTS = COM_NUM_POSTS - 1
	 WHERE COM_ID = (
	   SELECT COM_TOPIC_ID
		 FROM DELETED
	 );

    --DELETE 
	  --FROM DBO.COM_POST
     --WHERE COM_COMMENT_TO_ID = (
       --SELECT COM_ID
         --FROM DELETED
     --);
  
    -- If this post was an answer to a post, set the post that
	-- flagged this post as an answer as unanswered again
    UPDATE DBO.COM_TOPIC
       SET COM_ANSWER_POST_ID = NULL
     WHERE COM_ANSWER_POST_ID = (
       SELECT COM_ID
         FROM DELETED
     );
  
    -- If this post was the first post in a topic, update
	-- the topic to make it correct
    UPDATE DBO.COM_TOPIC
       SET COM_FIRST_POST_ID = (
         SELECT TOP 1 COM_ID
           FROM COM_POST
          WHERE COM_TOPIC_ID = (
         SELECT COM_TOPIC_ID
           FROM DELETED
         )
         ORDER BY COM_CREATED_DATE ASC
       )
       WHERE COM_ID = (
         SELECT COM_TOPIC_ID
           FROM DELETED
       );
  
    -- If this post was the last post in a topic, update
	-- the topic to make it correct
    UPDATE DBO.COM_TOPIC
       SET COM_LAST_POST_ID = (
         SELECT TOP 1 COM_ID
           FROM COM_POST
          WHERE COM_TOPIC_ID = (
            SELECT COM_TOPIC_ID
              FROM DELETED
          )
          ORDER BY COM_CREATED_DATE DESC
       )
       WHERE COM_ID = (
         SELECT COM_TOPIC_ID
           FROM DELETED
       );
	   
  END;

--
-- Create trigger DELETETOPICTRIGGER
--
-- NOTE: Updates the COM_NUM_TOPICS column in 
--       the COM_FORUM table and flags the post
--       and associated posts as deleted by setting
--       the COM_IS_DELETED column to '1'
--
CREATE TRIGGER DELETETOPICTRIGGER ON DBO.COM_TOPIC
  INSTEAD OF DELETE
AS 
  BEGIN
	 
	-- ADJUST THE NUMBER OF TOPICS IN THE FORUM
	UPDATE COM_FORUM
	   SET COM_NUM_TOPICS = COM_NUM_TOPICS - 1
	 WHERE COM_ID = (
       SELECT COM_FORUM_ID
		 FROM DELETED
	 );
	 
	-- FLAG THE TOPIC AS DELETED
	UPDATE DBO.COM_TOPIC
	   SET COM_IS_DELETED = 1
	 WHERE COM_ID = (
	   SELECT COM_ID
		 FROM DELETED
	 );
	 
	-- FLAG ALL POSTS IN THE TOPIC AS DELETED
    UPDATE DBO.COM_POST
	   SET COM_IS_DELETED = 1
	 WHERE COM_TOPIC_ID = (
	   SELECT COM_ID
		 FROM DELETED
	 );
	 
  END;
  
--
-- Create trigger INSERTPOSTTRIGGER
--
CREATE TRIGGER INSERTPOSTTRIGGER ON DBO.COM_POST
  AFTER INSERT
AS
  BEGIN

    DECLARE @PostId INT, @TopicId INT, @ForumId INT, @PostType VARCHAR(12), @NumReplies INT;
    DECLARE @Post CURSOR;
  
    SET @Post = CURSOR FOR
      SELECT COM_ID, COM_TOPIC_ID, COM_FORUM_ID, COM_TYPE FROM INSERTED;

	OPEN @Post;
    FETCH NEXT FROM @Post 
      INTO @PostId, @TopicId, @ForumId, @PostType;
      
	CLOSE @Post;
    DEALLOCATE @Post;	

    IF @PostType = 'answer'
      SET @NumReplies = 1;
    ELSE		
      SET @NumReplies = 0;
	  
	-- Update the number of posts in the associated forum
	UPDATE DBO.COM_FORUM
	   SET COM_NUM_POSTS = COM_NUM_POSTS + 1,
		   COM_LAST_POST_ID = @PostId
	 WHERE COM_ID = @ForumId;
	   
	IF @PostType <> 'question'
	  -- Update the number of posts in the associated topic
	  UPDATE DBO.COM_TOPIC
	     SET COM_NUM_POSTS = COM_NUM_POSTS + 1,
		     COM_NUM_ANSWERS = COM_NUM_ANSWERS + @NumReplies,
		     COM_LAST_POST_ID = @PostId
	   WHERE COM_ID = @TopicId;
	ELSE
	  UPDATE DBO.COM_TOPIC
	     SET COM_NUM_POSTS = 1,
		     COM_NUM_ANSWERS = 0,
			 COM_FIRST_POST_ID = @PostId,
		     COM_LAST_POST_ID = @PostId
	   WHERE COM_ID = @TopicId;
	   
	-- Update the ranking based on posting of the user
	UPDATE DBO.COM_MEMBER_RANKING
       SET COM_CURRENT_SCORE = COM_CURRENT_SCORE + 1
     WHERE COM_ID = (
       SELECT B.COM_ID 
	     FROM DBO.COM_POST A,
	          DBO.COM_MEMBER_RANKING B,
			  DBO.COM_RANKING C
	    WHERE A.COM_ID = @POSTID
	      AND A.COM_CREATED_BY_ID = B.COM_MEMBER_ID
		  AND B.COM_RANKING_ID = C.COM_ID
		  AND C.COM_TYPE = 'poster'
     );
	   
  END;
		
--
-- Create trigger INSERTTOPICTRIGGER
--
-- NOTE: Updates the COM_NUM_TOPICS column in 
--       the COM_FORUM table   
--
CREATE TRIGGER INSERTTOPICTRIGGER ON DBO.COM_TOPIC
  AFTER INSERT
AS 
  BEGIN
	  
	-- Update the number of topics in the associated forum
    UPDATE COM_FORUM
	   SET COM_NUM_TOPICS = COM_NUM_TOPICS + 1
	 WHERE COM_ID = (
	   SELECT COM_FORUM_ID
		 FROM INSERTED
	 );
    
  END;
  
--
-- Create trigger INSERTREPORTTRIGGER
--
-- NOTE: Updates the COM_IS_REPORTED column in 
--       the COM_POST table   
--
CREATE TRIGGER INSERTREPORTTRIGGER ON DBO.COM_REPORT
  AFTER INSERT
AS 
  BEGIN
	  
	-- Update the number of topics in the associated forum
    UPDATE COM_POST
	   SET COM_IS_REPORTED = 1
	 WHERE COM_ID = (
	   SELECT COM_POST_ID
		 FROM INSERTED
	 );
    
  END;
  	
--
-- Create trigger MESSAGETRIGGER
--
-- NOTE: Updates the COM_NUM_MSGS and COM_NUM_UNREAD_MSGS
--       columns in the COM_FOLDER table   
--
CREATE TRIGGER MESSAGETRIGGER ON DBO.COM_FOLDER_MESSAGE
  AFTER INSERT, UPDATE, DELETE
AS 

  IF EXISTS (SELECT * FROM INSERTED)
    BEGIN
	  IF EXISTS (SELECT * FROM DELETED) -- = UPDATE
	    BEGIN
		  UPDATE COM_FOLDER
	         SET COM_NUM_UNREAD_MSGS = COM_NUM_UNREAD_MSGS - 1
	       WHERE COM_ID = (
	         SELECT COM_FOLDER_ID
		       FROM INSERTED 
              WHERE COM_READ_DATE IS NOT NULL
	       );
		END;
	  ELSE -- = INSERT
	    UPDATE COM_FOLDER
	       SET COM_NUM_MSGS = COM_NUM_MSGS + 1,
		       COM_NUM_UNREAD_MSGS = COM_NUM_UNREAD_MSGS + 1
	     WHERE COM_ID = (
	        SELECT COM_FOLDER_ID
		      FROM INSERTED 
	     );
	  END;
  ELSE IF EXISTS(SELECT * FROM DELETED) -- = DELETED
    BEGIN
	  UPDATE COM_FOLDER
	     SET COM_NUM_MSGS = COM_NUM_MSGS - 1
	   WHERE COM_ID = (
	     SELECT COM_FOLDER_ID
		   FROM DELETED
	   );
    END;
	
--
-- Create trigger UPDATEPOSTTRIGGER
--
CREATE TRIGGER UPDATEPOSTTRIGGER ON DBO.COM_POST
  AFTER UPDATE
AS
  BEGIN

    DECLARE @PostId INT, @TopicId INT, @IsAnswer BIT;
    DECLARE @Post CURSOR;
  
    SET @Post = CURSOR FOR
      SELECT COM_ID, COM_TOPIC_ID, COM_IS_ANSWER FROM INSERTED;

	OPEN @Post;
    FETCH NEXT FROM @Post 
      INTO @PostId, @TopicId, @IsAnswer;
      
	CLOSE @Post;
    DEALLOCATE @Post;	

    IF @IsAnswer = 1
	  -- Update the topic and set the answer post id
	  UPDATE DBO.COM_TOPIC
	     SET COM_ANSWER_POST_ID = @PostId
	   WHERE COM_ID = @TopicId;
	   
  END;

--
-- Create trigger VOTETRIGGER
--
-- NOTE: Updates COM_POINTS_AWARDED column in COM_POST table
--       and COM_CURRENT_SCORE column in COM_MEMBER_RANKING table
--        
--  
CREATE TRIGGER VOTETRIGGER ON DBO.COM_VOTE
  AFTER INSERT
AS
  BEGIN

    DECLARE @PostId INT, @Points INT;
    DECLARE @Vote CURSOR;
  
    SET @Vote = CURSOR FOR
      SELECT COM_POST_ID, COM_POINTS FROM INSERTED; 
	
    OPEN @Vote;
    FETCH NEXT FROM @Vote 
      INTO @PostId, @Points;
    CLOSE @Vote;
    DEALLOCATE @Vote;	
  
    -- Update the points awarded in the post
    UPDATE DBO.COM_POST
       SET COM_POINTS_AWARDED = COM_POINTS_AWARDED + @Points
     WHERE COM_ID = @PostId;
   
    -- Update the ranking based on reputation for the user that created the post
    UPDATE DBO.COM_MEMBER_RANKING
       SET COM_CURRENT_SCORE = COM_CURRENT_SCORE + @Points
     WHERE COM_ID = (
       SELECT B.COM_ID 
	     FROM DBO.COM_POST A,
	          DBO.COM_MEMBER_RANKING B,
			  DBO.COM_RANKING C
	    WHERE A.COM_ID = @PostId
	      AND A.COM_CREATED_BY_ID = B.COM_MEMBER_ID
		  AND B.COM_RANKING_ID = C.COM_ID
		  AND C.COM_TYPE = 'reputation'
     );
   
  END;

--
-- Insert base data into table com_category
--
INSERT INTO COM_CATEGORY(COM_NAME, COM_IS_PUBLIC) VALUES('Internal', 0);

--
-- Insert base data into table com_forum
--
-- Note: These rows are intended for the internal category used to create
--       forums used for publishing news, blog posts, calendar events and
--       app showcase, thus enabling the re-use of forum functionality for
--       content management
--
INSERT INTO COM_FORUM(COM_CATEGORY_ID, COM_NAME, COM_DESC) VALUES(1, 'News', 'Internal forum for news content');
INSERT INTO COM_FORUM(COM_CATEGORY_ID, COM_NAME, COM_DESC) VALUES(1, 'Blog', 'Internal forum for blog posts');
INSERT INTO COM_FORUM(COM_CATEGORY_ID, COM_NAME, COM_DESC) VALUES(1, 'Calendar', 'Internal forum for calendar events');
INSERT INTO COM_FORUM(COM_CATEGORY_ID, COM_NAME, COM_DESC) VALUES(1, 'Showcase', 'Internal forum for app showcases');
