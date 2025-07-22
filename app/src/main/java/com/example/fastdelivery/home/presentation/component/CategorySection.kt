import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fastdelivery.product.presentation.viewmodel.ProductViewModel

@Composable
fun CategorySection(viewModel: ProductViewModel) {
    val categories = listOf("All", "Burger", "Chicken", "Pizza", "Empanadas", "Soup", "Salads", "Bread")
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory.equals(category, ignoreCase = true) ||
                    (selectedCategory == null && category == "All")

            val backgroundColor = if (isSelected) Color(0xCCFFA726) else MaterialTheme.colorScheme.surface

            val contentColor = if (isSelected) Color.Black
            else if (!isDarkTheme) MaterialTheme.colorScheme.onSurface
            else Color(0xFFB0B0B0)

            val borderColor = if (isSelected) Color(0xFFFFA726)
            else if (!isDarkTheme) MaterialTheme.colorScheme.outline
            else Color(0x66FFFFFF)

            Surface(
                color = backgroundColor,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .border(width = 1.dp, color = borderColor, shape = MaterialTheme.shapes.medium)
                    .clickable {
                        viewModel.updateSelectedCategory(
                            if (category.equals("All", ignoreCase = true)) null else category
                        )
                    }
                    .sizeIn(minWidth = 48.dp, minHeight = 40.dp)
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = contentColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
