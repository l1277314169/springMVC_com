package cn.mobilizer.channel.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mobilizer.channel.base.po.Knowledge;
import cn.mobilizer.channel.base.po.Knowledgeattachment;
import cn.mobilizer.channel.base.service.KnowledgeattachmentService;
import cn.mobilizer.channel.comm.vo.ResultMessage;
import cn.mobilizer.channel.comm.web.BaseActionSupport;
import cn.mobilizer.channel.exception.BusinessException;

@Controller
@RequestMapping(value = "/knowledgeattachmentController")
public class KnowledgeattachmentController extends BaseActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private KnowledgeattachmentService knowledgeattachmentService;
	
	/**
	 * 新增学习资料附件
	 * @param knowledgeId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showAddKnowledgeAttachment/{knowledgeId}")
	public String showAddKnowledgeAttachment(@PathVariable("knowledgeId")Integer knowledgeId,Model model){
		model.addAttribute("knowledgeId",knowledgeId);
		return "/base/showAddKnowledgeAttachment";
	}
	@ResponseBody
	@RequestMapping(value="/addKnowledgeAttachment",produces="application/json")
	public Object addKnowledgeAttachment(Integer knowledgeId,String fileName,Integer sequence)throws BusinessException
	{
		String fileName2=null;
		String fileSize=null;
		String[] fileNames=fileName.split(",");
		for(int i=0;i<fileNames.length;i++)
		{
			String fileName1=fileNames[i];
			String[] fileSizes=fileName1.split("@");
			for(int j=0;j<fileSizes.length-1;j++)
			{
				fileName2=fileSizes[j];
			    fileSize=fileSizes[j+1];
			}
		}
		
		if(log.isDebugEnabled())
		{
			log.debug("start method<addKnowledgeAttachment>");
		}
		int clientId = getClientId();
		Knowledgeattachment knowledgeattachment=new Knowledgeattachment();
		knowledgeattachment.setClientId(clientId);
		knowledgeattachment.setFileName(fileName2);
		knowledgeattachment.setFileSize(fileSize);
		knowledgeattachment.setKnowledgeId(knowledgeId);
		knowledgeattachment.setSequence(sequence);
		knowledgeattachmentService.addKnowledgeattachment(knowledgeattachment);
		return ResultMessage.ADD_SUCCESS_RESULT;
	}
	/**
	 * 编辑学习资料附件
	 * @param fileName
	 * @param KnowledgeId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
    @RequestMapping(value="/editKnowledgeAttachment")
    @ResponseBody
	public Object editKnowledgeAttachment(String fileName,Integer KnowledgeId,Model model)throws BusinessException{
    	System.out.println("KnowledgeId="+KnowledgeId);
    	System.out.println("fileName="+fileName);
		  model.addAttribute("fileName",fileName);
		return "/base/showEditKnowledge";
	}

}
