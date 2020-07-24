#!/bin/bash
for i in {0..100}
do
	i=$[($i*500)+1]
   echo "ssh -p 29418 azizn@review.openstack.org gerrit query --format=JSON --comments --patch-sets --start $i status:open > $i.txt"
   ssh -p 29418 azizn@review.openstack.org gerrit query --format=JSON --comments --patch-sets --start $i status:open > $i-open.txt
done