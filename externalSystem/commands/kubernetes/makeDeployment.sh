sed "s/AWS_ACCESS_KEY_VALUE/$AWS_ACCESS_KEY/g" ./kubernetes/deployment.yaml |\
sed "s/AWS_SECRET_ACCESS_KEY_VALUE/$AWS_SECRET_ACCESS_KEY/g" |\
kubectl apply -f -