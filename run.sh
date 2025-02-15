set -e

echo "Building the project"
./gradlew build

echo "running the app!"
java -jar app/build/libs/app.jar
