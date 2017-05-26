import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Representation of box to be displayed. Gives the position of box.
 * @author Avan
 *
 */
public class DisplayBox extends ImageView {	
	public DisplayBox(Image i, int row, int col)
	{
		this.setImage(i);
		this.p = new Position(row,col);
	}
	public Position getPosition()
	{
		return p;
	}
	public void setPosition(Position pos)
	{
		this.p = pos;
	}
	private Position p;
}
