PID=`lsof -i:8080 |grep -v "PID" | awk '{print $2}'`

if [ "" != "${PID}" ]; then
       kill -9 ${PID}
       echo "Kill guanguau  PID is ${PID}"
fi

java -jar guanguai-0.0.1-SNAPSHOT.jar --spring.profiles.active=online &
