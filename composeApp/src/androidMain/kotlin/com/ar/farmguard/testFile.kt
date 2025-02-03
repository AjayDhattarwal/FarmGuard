//
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp.Companion.Difference
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil3.compose.AsyncImage
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.news.domian.model.NewsItem
import com.ar.farmguard.weather.presentation.components.CelestialRiseAnimation
import org.brotli.dec.BrotliInputStream



@Preview(showBackground = true)
@Composable
fun PreviewWeatherDetailsScreen() {

//    Box(){
//        HomeScreeng()
//    }


}







//
//
//
////
//
//
////
//// Usage in HomeScreen
//@Composable
//fun HomeScreeng() {
//    val newsItems = remember {
//        // Sample data
//        listOf(
//            NewsItem(
//                id = "1",
//                title = "Revolutionary Quantum Computing Breakthrough Achieves Stable Qubits at Room Temperature",
//                source = "Tech Frontiers",
//                author = "Sarah Johnson",
//                category = "SCIENCE",
//                imageUrl = "https://example.com/quantum.jpg",
//                timestamp = "45m ago",
//                content = "..."
//            ),
//            NewsItem(
//                id = "2",
//                title = "Global Climate Agreement Reached: 195 Nations Pledge Carbon Neutrality by 2040",
//                source = "World Today",
//                author = null,
//                category = "POLITICS",
//                imageUrl = "https://example.com/climate.jpg",
//                timestamp = "2h ago",
//                content = "..."
//            ),
//            NewsItem(
//                id = "3",
//                title = "Global Climate Agreement Reached: 195 Nations Pledge Carbon Neutrality by 2040",
//                source = "World Today",
//                author = null,
//                category = "POLITICS",
//                imageUrl = "https://example.com/climate.jpg",
//                timestamp = "2h ago",
//                content = "..."
//            ),
//            NewsItem(
//                id = "4",
//                title = "Global Climate Agreement Reached: 195 Nations Pledge Carbon Neutrality by 2040",
//                source = "World Today",
//                author = null,
//                category = "POLITICS",
//                imageUrl = "https://example.com/climate.jpg",
//                timestamp = "2h ago",
//                content = "..."
//            ),
//            NewsItem(
//                id = "5",
//                title = "Global Climate Agreement Reached: 195 Nations Pledge Carbon Neutrality by 2040",
//                source = "World Today",
//                author = null,
//                category = "POLITICS",
//                imageUrl = "https://example.com/climate.jpg",
//                timestamp = "2h ago",
//                content = "..."
//            ),
//            NewsItem(
//                id = "6",
//                title = "Global Climate Agreement Reached: 195 Nations Pledge Carbon Neutrality by 2040",
//                source = "World Today",
//                author = null,
//                category = "POLITICS",
//                imageUrl = "https://example.com/climate.jpg",
//                timestamp = "2h ago",
//                content = "..."
//            )
//        )
//    }
//
//    Scaffold(
//        containerColor = MaterialTheme.colorScheme.background
//    ) { padding ->
//        Trending
//    }
//}
//
//
