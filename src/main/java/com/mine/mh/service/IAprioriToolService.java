package com.mine.mh.service;

public interface IAprioriToolService {

	/*
	 * 集合四位用户去过的景点
	 */
	public void getFourUserPoi();

	/**
	 * 判读字符数组array2是否包含于数组array1中
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public boolean iSStrContain(String[] array1, String[] array2);

	/**
	 * 根据产生的频繁项集输出关联规则
	 * 
	 * @param minConf
	 *            最小置信度阈值
	 */
	public int[] printAttachRule(double minConf);

	public int[] strToint(String[] unique);

}