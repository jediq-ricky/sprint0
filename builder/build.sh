. builder.conf

eval "tool=\$$1"

cd $1

dateTime=`date +"%Y%m%d%H%M"`

docker build -t $tool$dateTime .


