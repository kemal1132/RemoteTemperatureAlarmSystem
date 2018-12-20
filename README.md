usual Github work flow

--first time

git clone https://github.com/kemal1132/RemoteTemperatureAlarmSystem.git


--to add something

git pull origin master

git status //to check

git add -A

git commit -m "Your comment about what you did"

git push origin master



--if you are feeling fancy //big projects/jobs and stuff

git pull origin master

git branch devwork

git checkout devwork

git status//to check

git add -A

git commit -m "Your stuff"

git pull origin master

git merge master

git checkout master

git merge devwork

git push origin master

