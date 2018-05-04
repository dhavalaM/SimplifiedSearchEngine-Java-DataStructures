package edu.stevens.CS600;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url;
		System.out.println("args.length : "+args.length);
		if(args.length > 0)
		{
			//User provided url as argument, use that
			url = args[0];
			
		}
		else
		{
			//user did not provide any url, use default
			url="http://baldwingirls.edu.in/academics/every-child-matters/";
		}
			
		System.out.println("Fetching %s..."+ url);

		try {
			//parse the url, search words, print result
			new JSoupManager(url,args).parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
