/** 
 * DateUtils.java 
 * 
 * Project: IRS
 * 
 * Copyright Notice:
 * =============================================================================
 *    Copyright (c) 2007-2009 Shanghai Auto-Parts E-Procurement Co., Ltd. 
 *        All rights reserved.
 *
 *    This software is the confidential and proprietary information of
 *        APEP Co., Ltd. ("Confidential Information").
 *        You shall not disclose such Confidential Information and shall use it
 *        only in accordance with the terms of the license agreement you entered
 *        into with APEP.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.fyl.boot.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils{
	/**
	 * 格式24：yyyyMMddHHmmss
	 * <p>
	 * 格式中间无符号  、无横线
	 * <p>
	 */
	public static final String FULL_DATE24_NOT_LINE = "yyyyMMddHHmmss";

	/**
	 * 格式24：yyyy-MM-dd HH:mm:ss
	 */
	public static final String FULL_DATE24 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 格式12：yyyy-MM-dd hh:mm:ss
	 */
	public static final String FULL_DATE12 = "yyyy-MM-dd hh:mm:ss";

	/**
	 * 格式：yyyy-MM-dd HH:mm
	 */
	public static final String DATE24_NOT_S = "yyyy-MM-dd HH:mm";

	/**
	 * 格式：yyyy-MM-dd
	 */
	public static final String DATE = "yyyy-MM-dd";

	/**
	 * 格式24：yyyyMMdd
	 */
	public static final String DATE_NOT_LINE = "yyyyMMdd";

	/**
	 * 格式：MM-dd
	 */
	public static final String DATE_NOT_YEAR = "MM-dd";

	/**
	 * 格式：HH:mm:ss
	 */
	public static final String TIME_NOT_DATE = "HH:mm:ss";

	/**
	 * 格式：yyyy-MM
	 */
	public static final String DATE_NOT_DAY = "yyyy-MM";

	/**
	 * 格式：ddMMM  例如：13Mar
	 */
	public static final String DATE_M3_NOT_YEAR="ddMMM";

	/**
	 * 格式：ddMMMyy  例如：28Mar15
	 */
	public static final String DATE_M3="ddMMMyy";

	/**
	 * 输入一个时间格式的字符串，程序自动匹配相应的时间格式
	 * 如果输入的时间格式不存在，则返回null。此时建议使用stringToDate(String dateStr, String format)
	 * @param dateStr(要转为时间的字符串)
	 * @return Date 类型
	 */
	public static Date stringToDate(String dateStr) throws ParseException {
		if(dateStr==null||dateStr.trim().equals("")){
			return null;
		}
		SimpleDateFormat formater=null;
		Integer length=dateStr.length();
		switch(length){
			case 19:
				//格式24：yyyy-MM-dd HH:mm:ss
				formater=new SimpleDateFormat(FULL_DATE24);
				break;
			case 16:
				//格式：yyyy-MM-dd HH:mm
				formater=new SimpleDateFormat(DATE24_NOT_S);
				break;
			case 14:
				//格式：yyyy-MM-dd HH:mm
				formater=new SimpleDateFormat(FULL_DATE24_NOT_LINE);
				break;
			case 10:
				//格式：yyyy-MM-dd
				formater=new SimpleDateFormat(DATE);
				break;
			case 8:
				//格式：HH:mm:ss
				if(dateStr.contains(":")){
					formater=new SimpleDateFormat(TIME_NOT_DATE);
				}else{
					//格式：yyyyMMdd
					formater=new SimpleDateFormat(DATE_NOT_LINE);
				}
				break;
			case 7:
				//格式：yyyy-MM
				if(dateStr.indexOf("-")!=-1){
					formater=new SimpleDateFormat(DATE_NOT_DAY);
				}else{
					//格式：ddMMMyy
					formater=new SimpleDateFormat(DATE_M3,Locale.ENGLISH);
				}
				break;
			case 5:
				if(dateStr.indexOf("-")!=-1){
					//格式：MM-dd
					formater=new SimpleDateFormat(DATE_NOT_YEAR);
				}else{
					//格式：ddMMM
					formater=new SimpleDateFormat(DATE_M3_NOT_YEAR,Locale.ENGLISH);
				}

				break;
			default:
				formater=new SimpleDateFormat();
				break;
		}
		return formater.parse(dateStr);
	}
	
	public static int getDateInterval(Date beginDate, Date endDate){
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(beginDate);
		int beginYear = beginTime.get(Calendar.YEAR);
		int beginDay = beginTime.get(Calendar.DAY_OF_YEAR);
		
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(endDate);
		int endYear = endTime.get(Calendar.YEAR);
		int endDay = endTime.get(Calendar.DAY_OF_YEAR);
		
		if(beginYear == endYear){
			return endDay - beginDay + 1;
		}else{
			int dayCount = 0;
			Calendar cal = Calendar.getInstance();
			for(int year = beginYear; year <= endYear; year++){
				if(year == beginYear){
					int maxDay = beginTime.getMaximum(Calendar.DAY_OF_YEAR);
					dayCount += (maxDay - beginDay + 1);
				}else if(year == endYear){
					dayCount += endDay;
				}else{
					cal.set(Calendar.YEAR, year);
					dayCount += cal.getMaximum(Calendar.DAY_OF_YEAR);
				}
			}	
			return dayCount;
		}
		
		
	}
	
	public static DateRange getOneDateRange(Date date){
		DateRange dateRange = new DateRange();
		
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(date);
		beginTime.set(Calendar.HOUR_OF_DAY, 0);
		beginTime.set(Calendar.MINUTE, 0);
		beginTime.set(Calendar.SECOND, 0);
		beginTime.set(Calendar.MILLISECOND, 0);
		dateRange.setBeginTime(beginTime.getTime());
		
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(date);
		endTime.set(Calendar.DATE, endTime.get(Calendar.DATE) + 1);
		endTime.set(Calendar.HOUR_OF_DAY, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		endTime.set(Calendar.MILLISECOND, 0);
		dateRange.setEndTime(endTime.getTime());
		
		return dateRange;
	}

	public static DateRange getOneDayTimeRange(Date date){
		DateRange dateRange = new DateRange();

		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(date);
		beginTime.set(Calendar.HOUR_OF_DAY, 0);
		beginTime.set(Calendar.MINUTE, 0);
		beginTime.set(Calendar.SECOND, 0);
		beginTime.set(Calendar.MILLISECOND, 0);
		dateRange.setBeginTime(beginTime.getTime());

		Calendar endTime = Calendar.getInstance();
		endTime.setTime(date);
		endTime.set(Calendar.HOUR_OF_DAY, 23);
		endTime.set(Calendar.MINUTE, 59);
		endTime.set(Calendar.SECOND, 59);
		endTime.set(Calendar.MILLISECOND, 999);
		dateRange.setEndTime(endTime.getTime());

		return dateRange;
	}

	/**
	 * 得到指定月份最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastDay(int year, int month) {
		Calendar tCalendar = Calendar.getInstance();
		tCalendar.set(Calendar.YEAR, year);
		tCalendar.set(Calendar.MONTH, month - 1);
		return tCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getDateColumn(int type, int diff, int showType) {
		Calendar tCalendar = Calendar.getInstance();
		tCalendar.add(type, diff);
		int returnValue = tCalendar.get(showType);

		if (showType == Calendar.MONTH) {
			return returnValue + 1;
		} else {
			return returnValue;
		}
	}


	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取时间格式化
	 */
	public static String getStringDate(Date date,String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 获取现在时间
	 *
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getDateSeqWeek(Calendar c) {
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 取得当前日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}


	/**
	 *依据年周得到当周第一天的日期
	 * 
	 * @return
	 */
	public static Date getDateByYearWeek(int sYear, int sWeek) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, sYear);
		c.set(Calendar.WEEK_OF_YEAR, sWeek);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();

	}

	/**
	 *当前时间所在的年度年月
	 * 
	 * @return
	 */
	public static String getDateYearMonth(Calendar c) {
		String month = Integer.toString(c.get(Calendar.MONTH));
		if (month.length() == 1)
			month = "0" + month;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + month;

	}

	static String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	static String DAY_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	public static String dateToString(Date date) {
		return dateToString(date, DEFAULT_DATE_FORMAT_PATTERN);
	}

	public static String dateToDayString(Date date) {
		return dateToString(date, DAY_DATE_FORMAT_PATTERN);
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static Date stringToDate(String s, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(s);
	}

	/**
	 *时间差
	 * 
	 * @return
	 */
	public static String getTimeDiff(Date eDate, Date sDate){
		long timeDiff = (eDate.getTime() - sDate.getTime())/1000;
		long second = timeDiff%60;
		long minute = (timeDiff - second)%3600/60;
		long hour = (timeDiff -second - minute*60)%86400/3600;
		long day = (timeDiff -second - minute*60 - hour*3600)/86400;
		
		return (day>0?day+"天":"") + (hour>0?hour + "小时":"") + (minute>0?minute + "分钟":"") + (second>0?second + "秒":"");
	}

	/**
	 *时间差
	 *
	 * @return [天,时,分,秒]
	 */
	public static long[] getTimeDiffArray(Date eDate, Date sDate) {
		long timeDiff = (eDate.getTime() - sDate.getTime()) / 1000;
		long second = timeDiff % 60;
		long minute = (timeDiff - second) % 3600 / 60;
		long hour = (timeDiff - second - minute * 60) % 86400 / 3600;
		long day = (timeDiff - second - minute * 60 - hour * 3600) / 86400;
		long[] arr = {day, hour, minute, second};
		return arr;
	}

	public static Date getNextDay(Date date){
	
		Calendar firstDayCalendar = Calendar.getInstance();
		firstDayCalendar.setTime(date);
		
		firstDayCalendar.set(Calendar.DATE, firstDayCalendar.get(Calendar.DATE) + 1);
		firstDayCalendar.set(Calendar.HOUR_OF_DAY, 0);
		firstDayCalendar.set(Calendar.MINUTE, 0);
		firstDayCalendar.set(Calendar.SECOND, 0);
		firstDayCalendar.set(Calendar.MILLISECOND, 0);
		return firstDayCalendar.getTime();
	}

	/**
	 * 天开始时间
	 */
	public static Date getBeginTimeOfDay(Date date){
		Calendar firstDayCalendar = Calendar.getInstance();
		firstDayCalendar.setTime(date);
		
		firstDayCalendar.set(Calendar.HOUR_OF_DAY, 0);
		firstDayCalendar.set(Calendar.MINUTE, 0);
		firstDayCalendar.set(Calendar.SECOND, 0);
		firstDayCalendar.set(Calendar.MILLISECOND, 0);
		return firstDayCalendar.getTime();
	}

	/**
	 * 天结束时间
	 */
	public static Date getEndTimeOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	public static List<Date> getDateRangeStr(DateRange range){
		List<Date> dates = new ArrayList<Date>();
		Date beginDate = range.getBeginTime();
		do{
			dates.add(beginDate);
			beginDate = DateUtils.addDays(beginDate, 1);
		}while(beginDate.before(range.getEndTime()) || isSameDay(beginDate, range.getEndTime()));
		
		DateUtils.addDays(range.getBeginTime(), 1);
		return dates;
	}

	/**
	 * 最近num天日期集合
	 * @param num
	 * @return
	 */
	public static List<Date> getLastDateList(int num){
		DateRange range=new DateRange();
		range.setBeginTime(getIntervalCurrDay(-num));
		range.setEndTime(getIntervalCurrDay(-1));
		return getDateRangeStr(range);
	}

	public static DateRange getMonthDateRangeBeforeSpecifiedDate(Date date) {
		
		DateRange dateRange = new DateRange();
		
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(date);
		beginTime.set(Calendar.DAY_OF_MONTH, 1);
		beginTime.set(Calendar.MINUTE, 0);
		beginTime.set(Calendar.SECOND, 0);
		beginTime.set(Calendar.MILLISECOND, 0);
		dateRange.setBeginTime(beginTime.getTime());
		
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(date);
		endTime.set(Calendar.DATE, endTime.get(Calendar.DATE) + 1);
		endTime.set(Calendar.HOUR_OF_DAY, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		endTime.set(Calendar.MILLISECOND, 0);
		dateRange.setEndTime(endTime.getTime());
		
		return dateRange;
	}
	
	public static DateRange getMonthDateRange(int year, int month){
		DateRange dateRange = new DateRange();
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(Calendar.YEAR, year);
		beginTime.set(Calendar.MONTH, month);
		beginTime.set(Calendar.DAY_OF_MONTH, 1);
		beginTime.set(Calendar.MINUTE, 0);
		beginTime.set(Calendar.SECOND, 0);
		beginTime.set(Calendar.MILLISECOND, 0);
		dateRange.setBeginTime(beginTime.getTime());
		dateRange.setEndTime(DateUtils.addMonths(dateRange.getBeginTime(), 1));
		return dateRange;
	}

	public static int getDayInterval(Date beginDay, Date endDay) {
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime(beginDay);
		
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(endDay);
		
		long interval=(endTime.getTimeInMillis() - beginTime.getTimeInMillis())/1000;//秒
		long day=interval/(24*3600);//天
		return (int)day;
		
	}
	
	public static String getChDateWeek(Date currDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDay);
		String weekStr = "";
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			weekStr = "星期日";
			break;
		case 2:
			weekStr = "星期一";		
			break;
		case 3:
			weekStr = "星期二";
			break;
		case 4:
			weekStr = "星期三";
			break;
		case 5:
			weekStr = "星期四";
			break;
		case 6:
			weekStr = "星期五";
			break;
		case 7:
			weekStr = "星期六";
			break;
		default:
			break;
		}
		return weekStr;
	}

	public static String getChDateStr(Date currDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDay);
		return cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日";
	}

	/**
	 * 当前日期间隔dayNum天的日期
	 * @param dayNum 间隔天数
	 * @return
	 */
	public static Date getIntervalCurrDay(int dayNum){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + dayNum);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static int getYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH)+1;
	}

	public static int getDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}


	/**
	 * 时间范围bean
	 */
	public static class DateRange implements Serializable {

		private static final long serialVersionUID = -5236383035422821680L;

		public DateRange() {

		}

		public DateRange(Date beginTime, Date endTime) {
			this.beginTime = beginTime;
			this.endTime = endTime;
		}

		private Date beginTime;

		private Date endTime;

		public Date getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(Date beginTime) {
			this.beginTime = beginTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
	}


}
