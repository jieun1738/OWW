package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.DraftPlanVO;

import jakarta.servlet.http.HttpSession;

/** 임시 플랜(세션) 관리 서비스 */
public interface DraftPlanService {
	DraftPlanVO getCurrent(HttpSession session);

	void addOrUpdateCurrent(HttpSession session, int category, int productNo);

	void saveCurrentAsDraft(HttpSession session, String name);

	List<DraftPlanVO> getDrafts(HttpSession session);

	DraftPlanVO getByIndex(HttpSession session, int index);

	void removeByIndex(HttpSession session, int index);

	void clearCurrent(HttpSession session);
}
