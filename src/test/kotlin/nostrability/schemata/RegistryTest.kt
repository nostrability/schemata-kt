package nostrability.schemata

import kotlinx.serialization.json.jsonObject
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RegistryTest {

    @Test
    fun `get kind1Schema returns non-null`() {
        val schema = Schemata.get("kind1Schema")
        assertNotNull(schema, "kind1Schema should be available in the registry")
    }

    @Test
    fun `get noteSchema returns schema with dollar-schema field`() {
        val schema = Schemata.get("noteSchema")
        assertNotNull(schema, "noteSchema should be available")
        val obj = schema.jsonObject
        assertTrue(
            obj.containsKey("\$schema"),
            "noteSchema should contain a \$schema field"
        )
    }

    @Test
    fun `get nonexistent returns null`() {
        val schema = Schemata.get("nonexistent")
        assertNull(schema, "nonexistent key should return null")
    }

    @Test
    fun `keys has more than 100 entries`() {
        val allKeys = Schemata.keys()
        assertTrue(
            allKeys.size > 100,
            "Expected >100 keys, got ${allKeys.size}"
        )
    }

    @Test
    fun `get returns same instance on repeated calls (caching)`() {
        val first = Schemata.get("kind1Schema")
        val second = Schemata.get("kind1Schema")
        assertNotNull(first)
        assertNotNull(second)
        assertTrue(first === second, "Repeated get() should return cached instance")
    }

    @Test
    fun `nip11Schema is available`() {
        val schema = Schemata.get("nip11Schema")
        assertNotNull(schema, "nip11Schema should be available")
    }

    @Test
    fun `tag schemas are available`() {
        val schema = Schemata.get("pTagSchema")
        assertNotNull(schema, "pTagSchema should be available")
    }

    @Test
    fun `message schemas are available`() {
        val schema = Schemata.get("relayNoticeSchema")
        assertNotNull(schema, "relayNoticeSchema should be available")
    }
}
