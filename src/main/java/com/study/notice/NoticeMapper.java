package com.study.notice;

import java.util.List;
import java.util.Map;

public interface NoticeMapper {

	int create(NoticeDTO dto);

	NoticeDTO read(int noticeno);

	int update(NoticeDTO dto);

	int total(Map map);

	List<NoticeDTO> list(Map map);

	void upCnt(int noticeno);

	int passwd(Map map);

	int delete(int noticeno);

}
