RESOURCES_DIR := src/main/resources/schemas

.PHONY: vendor clean build test

vendor:
	@bash vendor-schemas.sh

clean:
	./gradlew clean

build: vendor
	./gradlew build

test: vendor
	./gradlew test
