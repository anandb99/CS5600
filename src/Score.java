import java.util.Map;
import java.util.Scanner;


public class Score 
{
	private static Scanner fip1;
    private static int count=0;
    private static int sum=0;
    private static float score=0;
	/*
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}
	 */

	public static float calculateScore(int index, String data, Map map)
	{
		score=0;
		fip1 = new Scanner(data);
		//System.out.println("\n **********  ********** ");
		Integer ONE = new Integer(1);
		count=0;
		
		while (fip1.hasNext()) 
		{
			count++;
			String s1 = fip1.next();
			s1 = s1.toLowerCase();
			
			// check keyword like tutorial
			
			//System.out.println("s1 : "+s1);
			java.util.Iterator it = map.entrySet().iterator();
			while (it.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it.next();
				//System.out.println(pair.getKey() + " = " + pair.getValue());

				if (s1.equals(pair.getKey().toString())) 
				{
					//System.out.println("match found @ : "+s1+" = "+pair.getKey().toString());
					Integer frequency = (Integer) map.get(s1);
					if (frequency == null) 
					{
						frequency = ONE;
					} else {
						int value = frequency.intValue();
						frequency = new Integer(value + 1);
					}
					map.put(s1, frequency);
				}
			}
		}
		System.out.println(" ********** NEW MAP **********");
		System.out.println(map);
		//		return (map);

		sum=0;
		java.util.Iterator it = map.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it.next();
			sum = sum + (int)pair.getValue();
		}
		score=(100-index)+(float)(sum/(float)count);

		return score;
	}
}
