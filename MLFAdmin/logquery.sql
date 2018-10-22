SELECT 
  log.logtime, 
  log.error, 
  log.message, 
  logmessages.messagedesc, 
  logerrors.errdesc, 
  log.conversationid
FROM 
  sdplog.log, 
  sdplog.logerrors, 
  sdplog.logmessages
WHERE 
  date(logtime)>= '2014-10-09'
  AND log.errorid = logerrors.errorid 
  AND log.messageid = logmessages.messageid;


SELECT 
  distinct(log.logid),
  log.logtime, 
  log.message, 
  log.messageid,
  logmessages.messagedesc, 
  log.conversationid
FROM 
  sdplog.log, 
  sdplog.logmessages
WHERE 
  date(logtime)>= date(now()) - interval '1 days'
  --AND logmessages.messageid != 0
  and log.errorid=0
  AND log.messageid = logmessages.messageid
  AND log.error=''
  order by log.logtime;


SELECT 
  distinct(log.logid),
  log.logtime, 
  log.error, 
  log.errorid,
  logerrors.errdesc, 
  log.conversationid
FROM 
  sdplog.log, 
  sdplog.logerrors
  --sdplog.logmessages
WHERE 
  date(logtime)>= date(now()) - interval '1 days'
  AND log.errorid = logerrors.errorid 
  --AND log.errorid != 0
  AND log.messageid = 0
  AND log.message=''
  order by logtime
;

select * from sdplog.log where logid=476;