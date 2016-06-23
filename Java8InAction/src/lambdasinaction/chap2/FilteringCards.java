package lambdasinaction.chap2;

import java.util.*;
import java.util.function.Predicate;

import com.github.wsppan.cardgames.playingcards.french.Deck;
import com.github.wsppan.cardgames.playingcards.french.Card;
import com.github.wsppan.cardgames.playingcards.french.Rank;
import com.github.wsppan.cardgames.playingcards.french.SuitColor;

public class FilteringCards {

    public static void main(String... args) {

	Deck deck = new Deck();

	// {{cards|1CLUBS|2CLUBS|3CLUBS|4CLUBS|5CLUBS|6CLUBS|7CLUBS|8CLUBS|9CLUBS|TCLUBS|JCLUBS|QCLUBS|KCLUBS|ACLUBS|1SPADES|2SPADES|3SPADES|4SPADES|5SPADES|6SPADES|7SPADES|8SPADES|9SPADES|TSPADES|JSPADES|QSPADES|KSPADES|ASPADES}}
	Deck blackCards = filterCardsByColor(deck, SuitColor.BLACK);
	System.out.println(blackCards.getWikiSyntax());

	// {{cards|1DIAMONDS|2DIAMONDS|3DIAMONDS|4DIAMONDS|5DIAMONDS|6DIAMONDS|7DIAMONDS|8DIAMONDS|9DIAMONDS|TDIAMONDS|JDIAMONDS|QDIAMONDS|KDIAMONDS|ADIAMONDS|1HEARTS|2HEARTS|3HEARTS|4HEARTS|5HEARTS|6HEARTS|7HEARTS|8HEARTS|9HEARTS|THEARTS|JHEARTS|QHEARTS|KHEARTS|AHEARTS}}
	Deck redCards = filterCardsByColor(deck, SuitColor.RED);
	System.out.println(redCards.getWikiSyntax());

	// {{cards|1CLUBS|2CLUBS|3CLUBS|4CLUBS|5CLUBS|6CLUBS|7CLUBS|8CLUBS|9CLUBS|TCLUBS|JCLUBS|QCLUBS|KCLUBS|ACLUBS|1SPADES|2SPADES|3SPADES|4SPADES|5SPADES|6SPADES|7SPADES|8SPADES|9SPADES|TSPADES|JSPADES|QSPADES|KSPADES|ASPADES}}
	Deck blackCards2 = filter(deck, new CardColorPredicate(SuitColor.BLACK));
	System.out.println(blackCards2.getWikiSyntax());

	// {{cards|TCLUBS|JCLUBS|QCLUBS|KCLUBS|ACLUBS|TDIAMONDS|JDIAMONDS|QDIAMONDS|KDIAMONDS|ADIAMONDS|THEARTS|JHEARTS|QHEARTS|KHEARTS|AHEARTS|TSPADES|JSPADES|QSPADES|KSPADES|ASPADES}}
	Deck faceCards = filter(deck, new FaceCardPredicate());
	System.out.println(faceCards.getWikiSyntax());

	//{{cards|TDIAMONDS|JDIAMONDS|QDIAMONDS|KDIAMONDS|ADIAMONDS|THEARTS|JHEARTS|QHEARTS|KHEARTS|AHEARTS}}
	Deck redAndFaceCards = filter(deck, new FaceCardAndColorPredicate(SuitColor.RED));
	System.out.println(redAndFaceCards.getWikiSyntax());

	// {{cards|TDIAMONDS|JDIAMONDS|QDIAMONDS|KDIAMONDS|ADIAMONDS|THEARTS|JHEARTS|QHEARTS|KHEARTS|AHEARTS}}
	Deck redCards2 = filter(deck, new CardPredicate() {
	    public boolean test(Card a) {
		return a.getColor().equals(SuitColor.RED);
	    }
	});
	System.out.println(redCards2.getWikiSyntax());

    }

    public static Deck filterblackCards(Deck deck) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (SuitColor.BLACK.equals(card.getColor())) {
		result.add(card);
	    }
	}
	return new Deck(result);
    }

    public static Deck filterCardsByColor(Deck deck, SuitColor color) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (card.getColor().equals(color)) {
		result.add(card);
	    }
	}
	return new Deck(result);
    }

    public static Deck filterCardsByRank(Deck deck, Rank rank) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (card.getRank().equals(rank)) {
		result.add(card);
	    }
	}
	return new Deck(result);
    }

    public static Deck filter(Deck deck, CardPredicate p) {
	List<Card> result = new ArrayList<>();
	for (Card card : deck) {
	    if (p.test(card)) {
		result.add(card);
	    }
	}
	return new Deck(result);
    }

    private interface CardPredicate extends Predicate<Card> {
	
	@Override
	public boolean test(Card a);
    }

    private static class FaceCardPredicate implements CardPredicate {
	
	@Override
	public boolean test(Card card) {
	    return card.getRank().asInt() > 9;
	}
    }

    private static class CardColorPredicate implements CardPredicate {
	private SuitColor suitColor;
	
	public CardColorPredicate(SuitColor suitColor) {
	    this.suitColor = suitColor;
	}
	
	@Override
	public boolean test(Card card) {
	    return suitColor.equals(card.getColor());
	}
    }

    private static class FaceCardAndColorPredicate implements CardPredicate {
	private SuitColor suitColor;
	
	public FaceCardAndColorPredicate(SuitColor suitColor) {
	    this.suitColor = suitColor;
	}
	
	@Override
	public boolean test(Card card) {
	    return suitColor.equals(card.getColor()) && card.getRank().asInt() > 9;
	}
    }
}