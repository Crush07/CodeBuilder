import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.hysea.hyseaappapi.entity.po.HyBlogComment;

@Mapper
public interface HyBlogCommentMapper extends BaseMapper<HyBlogComment> {

	Integer selectCountByCreateTime(Integer createTime);

	List<HyBlogComment> selectAllByCreateTime(Integer createTime);

	Integer deleteHyBlogCommentByCreateTime(Integer createTime);

	Integer updateHyBlogCommentByCreateTime(HyBlogComment hyBlogComment);

	Integer batchInsertHyBlogComment(@Param("hyBlogCommentList") List<HyBlogComment> hyBlogCommentList);

	Integer insertHyBlogComment(HyBlogComment hyBlogComment);

	List<HyBlogComment> selectAll();

}
