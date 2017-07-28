package com.sample;

import java.io.Serializable;

public class AuctionPOJO implements Serializable {

		private static final long serialVersionUID = 1L;
		// input fields
		private long auctionId;
		private double bid;
		private double bidTime;
		private String bidder;
		private double bidderRate;
		private double openBid;
		private double price;

		// Output Fields
		private String bidderCategory;
		private double auctioneerCommission;

		public AuctionPOJO() {
			super();
		}

		// getter and setter

		public long getAuctionId() {
			return auctionId;
		}

		public double getAuctioneerCommission() {
			return auctioneerCommission;
		}

		public void setAuctioneerCommission(double auctioneerCommission) {
			this.auctioneerCommission = bid * auctioneerCommission;
		}

		public String getBidderCategory() {
			return bidderCategory;
		}

		public void setBidderCategory(String bidderCategory) {
			this.bidderCategory = bidderCategory;
		}

		public void setAuctionId(long auctionId) {
			this.auctionId = auctionId;
		}

		public double getBid() {
			return bid;
		}

		public void setBid(double bid) {
			this.bid = bid;
		}

		public double getBidTime() {
			return bidTime;
		}

		public void setBidTime(double bidTime) {
			this.bidTime = bidTime;
		}

		public String getBidder() {
			return bidder;
		}

		public void setBidder(String bidder) {
			this.bidder = bidder;
		}

		public double getBidderRate() {
			return bidderRate;
		}

		public void setBidderRate(double bidderRate) {
			this.bidderRate = bidderRate;
		}

		public double getOpenBid() {
			return openBid;
		}

		public void setOpenBid(double openBid) {
			this.openBid = openBid;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public AuctionPOJO(long auctionId, double bid, double bidTime, String bidder, double bidderRate, double openBid,double price) {

			this.auctionId = auctionId;
			this.bid = bid;
			this.bidTime = bidTime;
			this.bidder = bidder;
			this.bidderRate = bidderRate;
			this.openBid = openBid;
			this.price = price;
		}

		// to print object
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			StringBuffer buffer = new StringBuffer();
			buffer.append("Auction Id=>" + getAuctionId() + "\n");
			buffer.append("bid amount=>" + getBid() + "\n");
			buffer.append("bidder=>" + getBidder() + "\n");
			buffer.append("Bidder Rate=>" + getBidderRate() + "\n");
			buffer.append("Bidder Category=>" + getBidderCategory() + "\n");
			buffer.append("Auctioneer Commission=>" + getAuctioneerCommission());

			return buffer.toString();
		}

	}
