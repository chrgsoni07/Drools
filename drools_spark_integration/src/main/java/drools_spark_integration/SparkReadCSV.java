package drools_spark_integration;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import java.util.List;
public class SparkReadCSV {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf();
		conf.setAppName("Spark App");
		conf.setMaster("local");

		JavaSparkContext ctx = new JavaSparkContext(conf);

		JavaRDD<String> data = ctx.textFile("C:/Users/CHIRAG/Desktop/Spark Input File/Auctions.csv");

		JavaRDD<AuctionPOJO> rdd_records = data.map(new Function<String, AuctionPOJO>() {
			int i = 0;
			public AuctionPOJO call(String line) throws Exception {
				String[] fields = line.split(",");
				if (i != 0) {
					return new AuctionPOJO(parseLong(fields[0]), parseDouble(fields[1]), parseDouble(fields[2]),
							fields[3].trim(), parseDouble(fields[4]), parseDouble(fields[5]), parseDouble(fields[6]));
				} else
					i++;return null;
			}

		});
		
		
		//Call drools method
	/*	rdd_records.foreach(new VoidFunction<AuctionPOJO>() {
			public void call(AuctionPOJO t) throws Exception {
				Drools.droolsCalling(t);
			}
			
		}); */
		
		List<AuctionPOJO> recordOutput = Drools.droolsCalling(rdd_records.collect());
		System.out.println(recordOutput.get(10));
		
		ctx.close();
		
	}

	
	
	public static long parseLong(String input) {
		return Long.parseLong(input.trim());
	}

	public static double parseDouble(String input) {
		return Double.parseDouble(input.trim());
	}

}
