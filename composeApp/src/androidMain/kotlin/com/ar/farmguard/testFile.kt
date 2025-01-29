
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp.Companion.Difference
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.ar.farmguard.core.presentation.shared.components.ContentTitle
import com.ar.farmguard.weather.presentation.components.CelestialRiseAnimation

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewWeatherDetailsScreen() {


    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Support for Value Addition - Support to R&G units",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.basicMarquee(
                    animationMode = MarqueeAnimationMode.Immediately,
                    repeatDelayMillis = 2000,
                    initialDelayMillis = 2000
                ),
            )

            Text(
                text = "Ministry Of Commerce And Industry",
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(0.7f),
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
//                scheme.fields.tags.forEach {
                    AssistChip(
                        onClick = {},
                        shape = MaterialTheme.shapes.small,
                        label = { Text(text = "Agriculture") },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.7f)
                        )
                    )
//                }
            }

            ContentTitle(
                title = "Scheme Details",
            ){
                Text(
                    text = AnnotatedString(text =
                            "The scheme \"Support for Value Addition - Support to R&G units\" is a Sub-Component of the Scheme \"Integrated Coffee Development Project\" by the Coffee Board, Department of Commerce, Ministry of Commerce and Industry. This scheme aims to enhance the quality of coffee products and achieve value addition through the introduction of improved technologies in roasting, grinding, and packaging which will result in boosting domestic coffee consumption and entrepreneurship in the coffee sector, especially in the non-traditional areas.")
                )
            }

            ContentTitle(
                title = "Benefits",
            ){
                Text(
                    text ="\"\\n1. Roasting Units, Gourmet roasting units 1Kg to &lt;10Kg/batch, and small roasting units with a capacity of less than 25 kg capacity are eligible for subsidy support of 40% of the machinery cost with a ceiling of ₹10,00,000.\\n1. For the SHGs, women entrepreneurs, SC/ ST, Minorities, and differently-abled beneficiaries, subsidy support is @50% of the machinery cost with a ceiling of ₹10,00,000.\\n1. Support for gourmet roaster units would enable the roasting of specialized blends in smaller quantities. This may also help to encourage a large number of small players/ new entrepreneurs to take up this venture in non-traditional coffee-drinking areas.\\n\\n<br>\\n\\n> **Disbursement of subsidy**\\n\\nThe subsidy shall be released to the applicant’s bank account **through PFMS** in cases where the application is complete in all respects and only after approval of the post-installation inspection report submitted by the Coffee Quality Division, Coffee Board, Bengaluru. \\n\""
                )
            }

        }
    }

}
