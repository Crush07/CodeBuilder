import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.hysea.hyseaappapi.entity.po.HyColumnBlog;

@Mapper
public interface HyColumnBlogMapper extends BaseMapper<HyColumnBlog> {

	Integer selectCountByCreateTime(Integer createTime);

	List<HyColumnBlog> selectAllByCreateTime(Integer createTime);

	Integer deleteHyColumnBlogByCreateTime(Integer createTime);

	Integer updateHyColumnBlogByCreateTime(HyColumnBlog hyColumnBlog);

	Integer batchInsertHyColumnBlog(@Param("hyColumnBlogList") List<HyColumnBlog> hyColumnBlogList);

	Integer insertHyColumnBlog(HyColumnBlog hyColumnBlog);

	List<HyColumnBlog> selectAll();

}
