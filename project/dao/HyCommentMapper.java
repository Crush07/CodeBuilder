import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.hysea.hyseaappapi.entity.po.HyComment;

@Mapper
public interface HyCommentMapper extends BaseMapper<HyComment> {

	Integer selectCountByCreateTime(Integer createTime);

	List<HyComment> selectAllByCreateTime(Integer createTime);

	Integer deleteHyCommentByCreateTime(Integer createTime);

	Integer updateHyCommentByCreateTime(HyComment hyComment);

	Integer batchInsertHyComment(@Param("hyCommentList") List<HyComment> hyCommentList);

	Integer insertHyComment(HyComment hyComment);

	List<HyComment> selectAll();

}
