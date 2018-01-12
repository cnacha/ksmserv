package com.mfu.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mfu.dao.queue.QueueEstimatedTimeDAO;
import com.mfu.entity.queue.QueueEstimatedTime;

@Controller
public class QueueEstimatedTimeController {
	@RequestMapping("/listQueueEstimatedTimeWS")
	public @ResponseBody List<QueueEstimatedTime> listQueueEstimatedTimeWS(HttpServletRequest request) {
		QueueEstimatedTimeDAO dao = new QueueEstimatedTimeDAO();

		List<QueueEstimatedTime> list = null;
		try {
			list = dao.getAllQueueEstimatedTime();
			dao.closeEntityManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@RequestMapping("/saveQueueEstimatedTimeWS")
	public @ResponseBody String saveQueueEstimatedTimeWS(@RequestBody QueueEstimatedTime QueueEstimatedTime) {
		QueueEstimatedTimeDAO dao = new QueueEstimatedTimeDAO();
		try {
			if (QueueEstimatedTime.getKey() == null) {
				dao.insertQueueEstimatedTime(QueueEstimatedTime);
				dao.closeEntityManager();
			} else {
				dao.updateQueueEstimatedTime(QueueEstimatedTime);
				dao.closeEntityManager();
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

	@RequestMapping("/deleteQueueEstimatedTimeWS")
	public @ResponseBody String deleteQueueEstimatedTimeWS(HttpServletRequest request) {
		QueueEstimatedTimeDAO dao = new QueueEstimatedTimeDAO();
		try {
			dao.deleteQueueEstimatedTime(request.getParameter("key"));
			dao.closeEntityManager();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
