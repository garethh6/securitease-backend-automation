
.PHONY: test clean allure-report docker-test

test:
	mvn -q -e -DskipTests=false test

clean:
	mvn -q clean

allure-report:
	mvn -q allure:report

docker-test:
	docker build -t securitease-backend .
	docker run --rm -v $(PWD)/target:/app/target securitease-backend