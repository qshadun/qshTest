object TestDate extends App {
	val sdf = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",java.util.Locale.US)
	sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
	println(sdf.parse("11/9/2012 9:15:13 PM"))
}