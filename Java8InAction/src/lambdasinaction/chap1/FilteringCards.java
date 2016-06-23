package lambdasinaction.chap1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.github.wsppan.cardgames.playingcards.french.Card;
import com.github.wsppan.cardgames.playingcards.french.Deck;
import com.github.wsppan.cardgames.playingcards.french.Suit;
import com.github.wsppan.cardgames.playingcards.french.SuitColor;

public class FilteringCards {

    public static void main(String... args) {

	Deck deck = new Deck();

	// [TCLUBS, JCLUBS, QCLUBS, KCLUBS, ACLUBS, TDIAMONDS, JDIAMONDS, QDIAMONDS, KDIAMONDS, ADIAMONDS, 
	// THEARTS, JHEARTS, QHEARTS, KHEARTS, AHEARTS, TSPADES, JSPADES, QSPADES, KSPADES, ASPADES]
	List<Card> faceCards = filterCards(deck, FilteringCards::isFaceCard);
	System.out.println(faceCards);
	
	// [TCLUBS, JCLUBS, QCLUBS, KCLUBS, ACLUBS, TDIAMONDS, JDIAMONDS, QDIAMONDS, KDIAMONDS, ADIAMONDS, 
	// THEARTS, JHEARTS, QHEARTS, KHEARTS, AHEARTS, TSPADES, JSPADES, QSPADES, KSPADES, ASPADES]
	List<Card> faceCards2 = filterCards(deck, (Card a) -> a.getRank().asInt() > 9);
	System.out.println(faceCards2);

	// [1DIAMONDS, 2DIAMONDS, 3DIAMONDS, 4DIAMONDS, 5DIAMONDS, 6DIAMONDS, 7DIAMONDS, 8DIAMONDS, 9DIAMONDS, 
	// TDIAMONDS, JDIAMONDS, QDIAMONDS, KDIAMONDS, ADIAMONDS, 1HEARTS, 2HEARTS, 3HEARTS, 4HEARTS, 5HEARTS, 
	// 6HEARTS, 7HEARTS, 8HEARTS, 9HEARTS, THEARTS, JHEARTS, QHEARTS, KHEARTS, AHEARTS]
	List<Card> redCards = filterCards(deck, FilteringCards::isRedCard);
	System.out.println(redCards);

	// [1DIAMONDS, 2DIAMONDS, 3DIAMONDS, 4DIAMONDS, 5DIAMONDS, 6DIAMONDS, 7DIAMONDS, 8DIAMONDS, 9DIAMONDS, 
	// TDIAMONDS, JDIAMONDS, QDIAMONDS, KDIAMONDS, ADIAMONDS, 1HEARTS, 2HEARTS, 3HEARTS, 4HEARTS, 5HEARTS, 
	// 6HEARTS, 7HEARTS, 8HEARTS, 9HEARTS, THEARTS, JHEARTS, QHEARTS, KHEARTS, AHEARTS]
	List<Card> redCards2 = filterCards(deck, (Card a) -> SuitColor.RED.equals(a.getColor()));
	System.out.println(redCards2);

	// [TDIAMONDS, JDIAMONDS, QDIAMONDS, KDIAMONDS, ADIAMONDS, THEARTS, JHEARTS, QHEARTS, KHEARTS, AHEARTS]
	List<Card> redFaceCards = filterCards(deck, (Card a) -> a.getRank().asInt() > 9 && SuitColor.RED.equals(a.getColor()));
	System.out.println(redFaceCards);
	
	// [TDIAMONDS, JDIAMONDS, QDIAMONDS, KDIAMONDS, ADIAMONDS]
	List<Card> diamondFaceCards = filterCards(deck, a -> a.getRank().asInt() > 9 && Suit.DIAMONDS.equals(a.getSuit()));
	System.out.println(diamondFaceCards);
	
	// {{cards|TDIAMONDS|JDIAMONDS|QDIAMONDS|KDIAMONDS|ADIAMONDS}}
	Deck filteredDeck = filterCardsDeck(deck, a -> a.getRank().asInt() > 9 && Suit.DIAMONDS.equals(a.getSuit()));
	System.out.println(filteredDeck.getWikiSyntax());
	
    }

    public static List<Card> filterRedCards(Deck deck) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (isRedCard(card)) {
		result.add(card);
	    }
	}
	return result;
    }

    public static List<Card> filterFaceCards(Deck deck) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (isFaceCard(card)) {
		result.add(card);
	    }
	}
	return result;
    }

    public static boolean isRedCard(Card card) {
	return SuitColor.RED.equals(card.getColor());
    }

    public static boolean isFaceCard(Card card) {
	return card.getRank().asInt() > 9;
    }

    public static List<Card> filterCards(Deck deck, Predicate<Card> p) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (p.test(card)) {
		result.add(card);
	    }
	}
	return result;
    }
    
    public static Deck filterCardsDeck(Deck deck, Predicate<Card> p) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (p.test(card)) {
		result.add(card);
	    }
	}
	return new Deck(result);
    }

}
