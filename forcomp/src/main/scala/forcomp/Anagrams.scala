package forcomp

import common._

object Anagrams {

  /** A word is simply a `String`. */
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]

  /** `Occurrences` is a `List` of pairs of characters and positive integers saying
   *  how often the character appears.
   *  This list is sorted alphabetically w.r.t. to the character in each pair.
   *  All characters in the occurrence list are lowercase.
   *  
   *  Any list of pairs of lowercase characters and their frequency which is not sorted
   *  is **not** an occurrence list.
   *  
   *  Note: If the frequency of some character is zero, then that character should not be
   *  in the list.
   */
  type Occurrences = List[(Char, Int)]

  /** The dictionary is simply a sequence of words.
   *  It is predefined and obtained as a sequence using the utility method `loadDictionary`.
   */
  val dictionary: List[Word] = loadDictionary

  /** Converts the word into its character occurence list.
   *  
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   */
  def wordOccurrences(w: Word): Occurrences = 
    w.toLowerCase().toList.groupBy((char: Char) => char).map(charAndCharList=>(charAndCharList._1, charAndCharList._2.length)).toList.sorted

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences = {
    s.map(word => wordOccurrences(word.toLowerCase())).flatten.groupBy(_._1).mapValues(elem => elem.map(_._2).sum).toList.sorted
    //val aa = collection.mutable.Map(a.toSeq: _*)
    //aa.groupBy(elem => elem._1)

  }
    //.foldLeft(List[Char, Int]())((acc, elem) => 
     // if acc._1==acc._2 acc :+ (elem._1, acc + elem._2) //toMap.groupby(elem => elem._1).toList.sorted//.map(elem => elem._1).groupby(elem=> elem._1).mapValues(elem => (elem._1, elem.map(elem._2).sum)).toList.sorted

  /** The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
   *  the words that have that occurrence count.
   *  This map serves as an easy way to obtain all the anagrams of a word given its occurrence list.
   *  
   *  For example, the word "eat" has the following character occurrence list:
   *
   *     `List(('a', 1), ('e', 1), ('t', 1))`
   *
   *  Incidentally, so do the words "ate" and "tea".
   *
   *  This means that the `dictionaryByOccurrences` map will contain an entry:
   *
   *    List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
   *
   */
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = dictionary.groupBy(wordOccurrences)

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = dictionaryByOccurrences.get(wordOccurrences(word)).getOrElse(List[Word]())

  /** Returns the list of all subsets of the occurrence list.
   *  This includes the occurrence itself, i.e. `List(('k', 1), ('o', 1))`
   *  is a subset of `List(('k', 1), ('o', 1))`.
   *  It also include the empty subset `List()`.
   * 
   *  Example: the subsets of the occurrence list `List(('a', 2), ('b', 2))` are:
   *
   *    List(
   *      List(),
   *      List(('a', 1)),
   *      List(('a', 2)),
   *      List(('b', 1)),
   *      List(('a', 1), ('b', 1)),
   *      List(('a', 2), ('b', 1)),
   *      List(('b', 2)),
   *      List(('a', 1), ('b', 2)),
   *      List(('a', 2), ('b', 2))
   *    )
   *
   *  Note that the order of the occurrence list subsets does not matter -- the subsets
   *  in the example above could have been displayed in some other order.
   */
  def combinations(occurrences: Occurrences): List[Occurrences] = {
    //The logic is that additional char makes new (original)*(the number of new char) Lists
    occurrences match{
      case List() => List(List())
      case elem::tail => {
        val tailCombination : List[Occurrences]= combinations(tail) //List[Occurrences] == List[List[Char,Int]]
        val occur : Occurrences= (1 until elem._2+1).map(i => (elem._1, i)).toList //occurrence, List[Char,Int]
        (occur.flatMap((x : (Char,Int))=> tailCombination.map((y: Occurrences) => List(x):::y)).toList):::tailCombination
      }
    }
  }

  /** Subtracts occurrence list `y` from occurrence list `x`.
   * 
   *  The precondition is that the occurrence list `y` is a subset of
   *  the occurrence list `x` -- any character appearing in `y` must
   *  appear in `x`, and its frequency in `y` must be smaller or equal
   *  than its frequency in `x`.
   *
   *  Note: the resulting value is an occurrence - meaning it is sorted
   *  and has no zero-entries.
   */
  def subtract(x: Occurrences, y: Occurrences): Occurrences = {
    y.foldLeft(x)((acc: Occurrences, yy: (Char,Int)) => {
      //remove all of char from acc
      if(acc.contains(yy)) acc.filterNot(List(yy).toSet)
      //if not, update count
      else acc.toMap.updated(yy._1, acc.toMap.apply(yy._1) - yy._2).toList.sorted
    })

    /*
*** Wrong: Below is about removing whole set of y from x, not decrementing count*** 
    Below is the logic.
    1. make overlapList which is List[Occurences] consisting of List(x - {one element in y})
    2. using foldLeft to make complete subtract list by intersect all of lists in overlapList : A and B and C and ... 
    
    val overlapList = y.map((yy:(Char,Int)) => x.filter((xx:(Char,Int)) => xx!=yy)).toList
    overlapList.foldLeft(x)((a,b)=>a intersect b)
*** */
  }

  /** Returns a list of all anagram sentences of the given sentence.
   *  
   *  An anagram of a sentence is formed by taking the occurrences of all the characters of
   *  all the words in the sentence, and producing all possible combinations of words with those characters,
   *  such that the words have to be from the dictionary.
   *
   *  The number of words in the sentence and its anagrams does not have to correspond.
   *  For example, the sentence `List("I", "love", "you")` is an anagram of the sentence `List("You", "olive")`.
   *
   *  Also, two sentences with the same words but in a different order are considered two different anagrams.
   *  For example, sentences `List("You", "olive")` and `List("olive", "you")` are different anagrams of
   *  `List("I", "love", "you")`.
   *  
   *  Here is a full example of a sentence `List("Yes", "man")` and its anagrams for our dictionary:
   *
   *    List(
   *      List(en, as, my),
   *      List(en, my, as),
   *      List(man, yes),
   *      List(men, say),
   *      List(as, en, my),
   *      List(as, my, en),
   *      List(sane, my),
   *      List(Sean, my),
   *      List(my, en, as),
   *      List(my, as, en),
   *      List(my, sane),
   *      List(my, Sean),
   *      List(say, men),
   *      List(yes, man)
   *    )
   *
   *  The different sentences do not have to be output in the order shown above - any order is fine as long as
   *  all the anagrams are there. Every returned word has to exist in the dictionary.
   *  
   *  Note: in case that the words of the sentence are in the dictionary, then the sentence is the anagram of itself,
   *  so it has to be returned in this list.
   *
   *  Note: There is only one anagram of an empty sentence.
   */
  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
    /*
    Below is the logic.
    1. Give sentence occurrence to "makeSentences"
    2. "makeSentences" will produce combinations, which produce available words from one of the combination elements.
    3. The rest of the elements of combination will make the other available words which will connected to 2
    */
    def makeSentences(curOccurrence: Occurrences): List[Sentence] = {
      curOccurrence match{
        case List() => List(List()) //if empty
        case _ => {
          for{
            //combinations ~ : List[Occurrences], combElement: Occurrences
            combElement <- combinations(curOccurrence)
            //dictonaryByOccurrences ~ : List[Word], wordsAvailable: Word
            wordsAvailable <- dictionaryByOccurrences.getOrElse(combElement, List[Word]()) //it will return List() if there doesn't exist any words from dictionary
            //makeSentences ~ : List[Sentence], restWordsAvaiable: Sentence==List[Word]
            restWordsAvailable <- makeSentences(subtract(curOccurrence, combElement))
          } yield wordsAvailable::restWordsAvailable
        }
      }
    }
    makeSentences(sentenceOccurrences(sentence))
  }
}
