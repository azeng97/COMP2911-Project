import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Representation of box to be displayed. Gives the position of box.
 * @author Avan
 *
 */
public class DisplayBox extends ImageView {
	/**
	 * Creates DisplayBox object from image and position index
	 * @param i	image to display for box
	 * @param row	integer row of box
	 * @param col	integer column of box
	 */
	public DisplayBox(Image i, int row, int col)
	{
		this.setImage(i);
		this.p = new Position(row,col);
	}
	/**
	 * get position of display box
	 * @return	position of box
	 */
	public Position getPosition()
	{
		return p;
	}
	/**
	 * Set position of display box
	 * @param pos	position of box
	 */
	public void setPosition(Position pos)
	{
		this.p = pos;
	}
	private Position p;
}
