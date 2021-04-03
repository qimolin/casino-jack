package casino.cashier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CardIDTest {

    @Test
    public void CardID_shouldGenerateAndReturnAnID(){
        CardID sut = new CardID();
        String id = sut.getID();
        System.out.println(id);
        assertThat(id).isNotNull();
    }

}