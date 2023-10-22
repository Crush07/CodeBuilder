import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.hysea.hyseaappapi.entity.po.HyColumn;

@Mapper
public interface HyColumnMapper extends BaseMapper<HyColumn> {

	Integer selectCountByColumCover(String columCover);

	List<HyColumn> selectAllByColumCover(String columCover);

	Integer deleteHyColumnByColumCover(String columCover);

	Integer updateHyColumnByColumCover(HyColumn hyColumn);

	Integer batchInsertHyColumn(@Param("hyColumnList") List<HyColumn> hyColumnList);

	Integer insertHyColumn(HyColumn hyColumn);

	List<HyColumn> selectAll();

}
