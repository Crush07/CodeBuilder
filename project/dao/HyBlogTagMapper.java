import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.hysea.hyseaappapi.entity.po.HyBlogTag;

@Mapper
public interface HyBlogTagMapper extends BaseMapper<HyBlogTag> {

	Integer selectCountByTagId(String tagId);

	List<HyBlogTag> selectAllByTagId(String tagId);

	Integer deleteHyBlogTagByTagId(String tagId);

	Integer updateHyBlogTagByTagId(HyBlogTag hyBlogTag);

	Integer batchInsertHyBlogTag(@Param("hyBlogTagList") List<HyBlogTag> hyBlogTagList);

	Integer insertHyBlogTag(HyBlogTag hyBlogTag);

	List<HyBlogTag> selectAll();

}
