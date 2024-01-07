grep -n ./user/logs/logback.log -e "ERROR"
grep -n ./file/logs/logback.log -e "ERROR"
grep -n ./externalSystem/logs/logback.log -e "ERROR"

grep -n ./message/logs/logback.log -e "ERROR"
grep -n ./room/logs/logback.log -e "ERROR"

grep -n ./collectedData/logs/logback.log -e "ERROR"
grep -n ./gateway/logs/logback.log -e "ERROR"