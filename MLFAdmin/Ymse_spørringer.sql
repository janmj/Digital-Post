SELECT distinct(log.logid),log.logtime,log.message,log.messageid,logmessages.messagedesc, log.conversationid 
FROM
sdplog.log, sdplog.logmessages
WHERE logtime >= now() - interval '36 hours' 
AND log.errorid=0  
AND log.messageid = logmessages.messageid 
AND log.error=''


select count(forsendelsesid) from sdp.forsendelser
where date(odato)>=date(now());

select count(kvitteringsid) from sdp.kvitteringer
where date(odato)>=date(now());


select count(*) from sdp.forsendelser where status=0;



select count(kvitteringsid) from sdp.kvitteringer
where date(odato)>='2014-10-21';

select count(forsendelsesid) from sdp.forsendelser
where date(odato)>='2014-10-21';

select count(forsendelsesid) from sdp.forsendelser where date(odato)='2014-10-22'

select count(kvitteringsid) from sdp.kvitteringer where date(odato)='2014-10-22'

select max(odato) from sdp.forsendelser;

select max(odato) from sdp.kvitteringer;

select * from sdplog.log where conversationid = '58e5506d-1e74-4631-af29-a68af5e41de9' order by logtime;


select l.logid,  
from 
sdp.forsendelse f, sdp.kvitteringer k, sdplog.log l
where 

select forsendelsesid,conversationid,status,description,odato,edato 
from sdp.forsendelser , sdp.forsendelser_status
where date(odato)='2014-10-28'
and forsendelser.status = forsendelser_status.id
;

select kvitteringsid,conversationid,status,odato,edato,kvittering_text,description
from sdp.kvitteringer, sdp.kvitteringer_status
where date(odato) = '2014-10-28'
and kvitteringer.status=kvitteringer_status.id;












