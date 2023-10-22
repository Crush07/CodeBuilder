import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.hysea.hyseaappapi.entity.po.HyBlog;

@Mapper
public interface HyBlogMapper extends BaseMapper<HyBlog> {

	Integer selectCountByCreateTime(Integer createTime);

	List<HyBlog> selectAllByCreateTime(Integer createTime);

	Integer deleteHyBlogByCreateTime(Integer createTime);

	Integer updateHyBlogByCreateTime(HyBlog hyBlog);

	Integer batchInsertHyBlog(@Param("hyBlogList") List<HyBlog> hyBlogList);

	Integer insertHyBlog(HyBlog hyBlog);

	List<HyBlog> selectAll();

}
