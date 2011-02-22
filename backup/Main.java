public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Database db = Database.getInstance();
		for(int l = 0 ; l < 3 ; l++){
			for(int y = 0 ; y < 11 ; y++){
				for(int x = 0 ; x < 14 ; x++){
					System.out.print(db.getTile(0,l, x, y)+",");
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}

}
