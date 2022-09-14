package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s_union_s1_s2 = union(s1, s2)
    val s_union_s2_s3 = union(s2, s3)
    val s_union_all = union(union(s1, s2), s3)
    val f_is_2 = (arg: Int) => (arg==2)
    val f_is_positive = (arg: Int) => (arg > 0)
    val f_square = (arg: Int) => (arg*arg)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains common elements"){
    new TestSets{
      val s = intersect(s_union_s1_s2, s_union_s2_s3)
      assert(contains(s, 2), "Intersect 1")
      assert(!contains(s, 1), "Intersect 2")
      assert(!contains(s, 3), "Intersect 3")
      assert(!contains(s, 4), "Intersect 4")
    }
  }

  test("diff contains the difference of elements"){
    new TestSets{
      val s = diff(s_union_s1_s2, s_union_s2_s3)
      assert(contains(s, 1), "Diff 1")
      assert(!contains(s, 2), "Diff 2")
      assert(!contains(s, 3), "Diff 3")
      assert(!contains(s, 4), "Diff 4")
    }
  }

  test("filter contains subset of s where filter holds"){
    new TestSets{
      val s_all_filter_2 = filter(s_union_all, f_is_2)
      val s1_filter_2 = filter(s1, f_is_2)
      val s_all_filter_postive = filter(s_union_all, f_is_positive)
      assert(contains(s_all_filter_2, 2), "Filter 1")
      assert(!contains(s_all_filter_2, 1), "Filter 2")
      assert(!contains(s1_filter_2, 2), "Filter 3")
      assert(!contains(s1_filter_2, 1), "Filter 4")

      assert(contains(s_all_filter_postive, 1), "Filter 5")
      assert(contains(s_all_filter_postive, 2), "Filter 6")
      assert(contains(s_all_filter_postive, 3), "Filter 7")
    }
  }

  test("forall checks s satisfy p"){
    new TestSets{
      val s0 = singletonSet(0)
      val s_nonnegative = union(s0, s_union_all)
      assert(forall(s2, f_is_2), "Set {2} and filter x==2")
      assert(!forall(s_union_all, f_is_2), "Set {1,2,3} and filter x==2")
      assert(forall(s_union_all, f_is_positive), "Set {1,2,3} and filter x > 0")
      assert(!forall(s_nonnegative, f_is_positive), "Set {0,1,2,3} and filter x > 0")
    }
  }

  test("exists checks at least 1 element of s satisfies p"){
    new TestSets{
      val s0 = singletonSet(0)
      val s_nonnegative = union(s0, s_union_all)
      assert(exists(s2, f_is_2), "Set {2} and filter x==2")
      assert(exists(s_union_all, f_is_2), "Set {1,2,3} and filter x==2")
      assert(exists(s_union_all, f_is_positive), "Set {1,2,3} and filter x > 0")
      assert(exists(s_nonnegative, f_is_positive), "Set {0,1,2,3} and filter x > 0")
      assert(!exists(s0, f_is_positive), "Set {0} and filter x > 0")
    }
  }

  test("map returns transformed set of s"){
    new TestSets{
      val s_square = map(s_union_all, f_square)
      assert(contains(s_square, 1), "Set {1,2,3} and map square: contains 1?")
      assert(contains(s_square, 4), "Set {1,2,3} and map square: contains 4?")
      assert(contains(s_square, 9), "Set {1,2,3} and map square: contains 9?")
      assert(!contains(s_square, 2), "Set {1,2,3} and map square: contains 2?")
      assert(!contains(s_square, 3), "Set {1,2,3} and map square: contains 3?")
    }
  }
}