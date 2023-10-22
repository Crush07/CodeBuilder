import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.hysea.hyseaappapi.entity.po.HyBlogPraise;

@Mapper
public interface HyBlogPraiseMapper extends BaseMapper<HyBlogPraise> {

	Integer selectCountByPraiseUserId(String praiseUserId);

	List<HyBlogPraise> selectAllByPraiseUserId(String praiseUserId);

	Integer deleteHyBlogPraiseByPraiseUserId(String praiseUserId);

	Integer updateHyBlogPraiseByPraiseUserId(HyBlogPraise hyBlogPraise);

	Integer batchInsertHyBlogPraise(@Param("hyBlogPraiseList") List<HyBlogPraise> hyBlogPraiseList);

	Integer insertHyBlogPraise(HyBlogPraise hyBlogPraise);

	List<HyBlogPraise> selectAll();

}
