source version.properties
echo "This is the entrypoint script executing jar file: $IMAGE_VERSION"
echo "Image version: $IMAGE_VERSION"
java -jar "$IMAGE_VERSION.jar"
