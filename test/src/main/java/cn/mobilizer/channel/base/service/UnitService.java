package cn.mobilizer.channel.base.service;

import java.util.List;

import cn.mobilizer.channel.base.po.Unit;
import cn.mobilizer.channel.comm.web.BasicService;

public interface UnitService extends BasicService<Unit> {

	List<Unit> getUnitList(int clientId);

	Unit getUnit(Integer unitId);

}
