# ECSE 429 - Term Project - Group 10 - Acceptance Testing

## Members

* Michael MAATOUK - 260554267
* Lucien GEORGE - 260554267
* Alexander BRATYSHKIN - 260684228
* Nathan LAFRANCE-BERGER - 260686487
* Benjamin WILLMS - 260690005

## Test status

First part - As of Sunday, October 15, 21:57:

![test results](https://i.imgur.com/hXHA08l.png)

Third part - As of Thursday, December 7, 23:43

![test results2](https://i.imgur.com/mTpYvoN.png)

## Requirements

In order to run the tests, **the user must have Apache Maven installed in her
system**. Maven is a software project management and comprehension tool similar
to Gradle and SBT. Please refer to
[this comprehensive and official guide](https://maven.apache.org/install.html)
for instructions on how to get the tool setup on a system across multiple
operating systems.

## Installation of Dependencies

Once this repository is cloned on your local machine, navigate to the root
folder of this project through your command prompt and run `mvn install` to
install all required dependencies to run the tests. **This step is absolute
mandatory before running any tests**. Otherwise, you will not be able to utilize
the Jukito and Apache Commons Collections dependencies that we use in our tests.

## Running Tests

To run the tests, simply execute the `mvn test` command inside of the root
folder of this project from your command prompt.

## Important Notes

* Somehow, we have been having trouble setting up this project for Eclipse. If
  you do decide to, for some reason, import this project into your IDE for
  (perhaps?) better test visualization, then please import it as a Maven project
  in IntelliJ.

* The `shouldInsertAndRemoveFasterThanArrayListAndLinkedList()` test inside of
  the `ListImplementationsTest.java` test suite might fail occasionally. This is
  due to the fact that we kept the variable `CONST_SIZE` relatively low, so as
  to remove the overhead caused by this particular test when running all of our
  tests. If you want to ensure that this test passes every single time it is
  execute, please increase the value of `CONST_SIZE` by a factor of 10, to the
  detriment of overall execution time.

* The tests for the first part have been annotated with an `@Ignore` tag.
