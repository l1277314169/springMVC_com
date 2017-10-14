package www.lvmama.com.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity(name = "card")
public class CardEntity implements Serializable{
  private static final long serialVersionUID = -4647063408429910305L;
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  private String cardcode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCardcode() {
    return cardcode;
  }

  public void setCardcode(String cardcode) {
    this.cardcode = cardcode;
  }
}
