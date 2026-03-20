package nostrability.schemata

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * Registry of compiled Nostr JSON schemas.
 *
 * Schemas are loaded lazily from classpath resources (vendored JSON files)
 * and cached after first access.
 */
object Schemata {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Map from schema key to classpath resource path.
     * Generated from schema_registry.tsv — paths have `../` prefix stripped
     * and `schemas/` prepended for classpath resource loading.
     */
    private val registry: Map<String, String> = mapOf(
        // nip-01
        "kind0ContentSchema" to "schemas/nips/nip-01/kind-0/schema.content.json",
        "kind0Schema" to "schemas/nips/nip-01/kind-0/schema.json",
        "kind1Schema" to "schemas/nips/nip-01/kind-1/schema.json",
        "clientCloseSchema" to "schemas/nips/nip-01/messages/client-close/schema.json",
        "clientEventSchema" to "schemas/nips/nip-01/messages/client-event/schema.json",
        "clientReqSchema" to "schemas/nips/nip-01/messages/client-req/schema.json",
        "filterSchema" to "schemas/nips/nip-01/messages/filter/schema.json",
        "relayClosedSchema" to "schemas/nips/nip-01/messages/relay-closed/schema.json",
        "relayEoseSchema" to "schemas/nips/nip-01/messages/relay-eose/schema.json",
        "relayEventSchema" to "schemas/nips/nip-01/messages/relay-event/schema.json",
        "relayNoticeSchema" to "schemas/nips/nip-01/messages/relay-notice/schema.json",
        "relayOkSchema" to "schemas/nips/nip-01/messages/relay-ok/schema.json",
        "noteSchema" to "schemas/nips/nip-01/note/schema.json",
        "noteUnsignedSchema" to "schemas/nips/nip-01/note-unsigned/schema.json",
        "secp256k1Schema" to "schemas/nips/nip-01/secp256k1/schema.json",
        "aTagSchema" to "schemas/nips/nip-01/tag/a/schema.json",
        "dTagSchema" to "schemas/nips/nip-01/tag/d/schema.json",
        "eTagSchema" to "schemas/nips/nip-01/tag/e/schema.json",
        "pTagSchema" to "schemas/nips/nip-01/tag/p/schema.json",
        "tagSchema" to "schemas/nips/nip-01/tag/schema.json",
        "tTagSchema" to "schemas/nips/nip-01/tag/t/schema.json",
        // nip-02
        "kind3ContentSchema" to "schemas/nips/nip-02/kind-3/schema.content.json",
        "kind3Schema" to "schemas/nips/nip-02/kind-3/schema.json",
        // nip-04
        "kind4Schema" to "schemas/nips/nip-04/kind-4/schema.json",
        // nip-05
        "identifierSchema" to "schemas/nips/nip-05/identifier/schema.json",
        "wellKnownSchema" to "schemas/nips/nip-05/well-known/schema.json",
        // nip-09
        "kind5Schema" to "schemas/nips/nip-09/kind-5/schema.json",
        // nip-11
        "nip11Schema" to "schemas/nips/nip-11/schema.json",
        // nip-17
        "kind10050Schema" to "schemas/nips/nip-17/kind-10050/schema.json",
        "kind14Schema" to "schemas/nips/nip-17/kind-14/schema.json",
        "kind15Schema" to "schemas/nips/nip-17/kind-15/schema.json",
        "blurhashTagSchema" to "schemas/nips/nip-17/tag/blurhash/schema.json",
        "decryptionkeyTagSchema" to "schemas/nips/nip-17/tag/decryption-key/schema.json",
        "decryptionnonceTagSchema" to "schemas/nips/nip-17/tag/decryption-nonce/schema.json",
        "dimTagSchema" to "schemas/nips/nip-17/tag/dim/schema.json",
        "encryptionalgorithmTagSchema" to "schemas/nips/nip-17/tag/encryption-algorithm/schema.json",
        "fallbackTagSchema" to "schemas/nips/nip-17/tag/fallback/schema.json",
        "filetypeTagSchema" to "schemas/nips/nip-17/tag/file-type/schema.json",
        "oxTagSchema" to "schemas/nips/nip-17/tag/ox/schema.json",
        "relayTagSchema" to "schemas/nips/nip-17/tag/relay/schema.json",
        "sizeTagSchema" to "schemas/nips/nip-17/tag/size/schema.json",
        "subjectTagSchema" to "schemas/nips/nip-17/tag/subject/schema.json",
        "thumbTagSchema" to "schemas/nips/nip-17/tag/thumb/schema.json",
        "xTagSchema" to "schemas/nips/nip-17/tag/x/schema.json",
        // nip-18
        "kind16Schema" to "schemas/nips/nip-18/kind-16/schema.json",
        "kind6Schema" to "schemas/nips/nip-18/kind-6/schema.json",
        "kTagSchema" to "schemas/nips/nip-18/tag/k/schema.json",
        "qTagSchema" to "schemas/nips/nip-18/tag/q/schema.json",
        // nip-22
        "kind1111Schema" to "schemas/nips/nip-22/kind-1111/schema.json",
        "ATagSchema" to "schemas/nips/nip-22/tag/_A/schema.json",
        "ETagSchema" to "schemas/nips/nip-22/tag/_E/schema.json",
        "KTagSchema" to "schemas/nips/nip-22/tag/_K/schema.json",
        "PTagSchema" to "schemas/nips/nip-22/tag/_P/schema.json",
        // nip-23
        "kind30023Schema" to "schemas/nips/nip-23/kind-30023/schema.json",
        "kind30024Schema" to "schemas/nips/nip-23/kind-30024/schema.json",
        "publishedatTagSchema" to "schemas/nips/nip-23/tag/published_at/schema.json",
        // nip-25
        "kind17Schema" to "schemas/nips/nip-25/kind-17/schema.json",
        "kind7Schema" to "schemas/nips/nip-25/kind-7/schema.json",
        "ereactTagSchema" to "schemas/nips/nip-25/tag/e-react/schema.json",
        "emojiTagSchema" to "schemas/nips/nip-25/tag/emoji/schema.json",
        // nip-32
        "kind1985Schema" to "schemas/nips/nip-32/kind-1985/schema.json",
        "LTagSchema" to "schemas/nips/nip-32/tag/L/schema.json",
        "lTagSchema" to "schemas/nips/nip-32/tag/l/schema.json",
        // nip-34
        "kind1617Schema" to "schemas/nips/nip-34/kind-1617/schema.json",
        "kind1621Schema" to "schemas/nips/nip-34/kind-1621/schema.json",
        "kind1630Schema" to "schemas/nips/nip-34/kind-1630/schema.json",
        "kind1631Schema" to "schemas/nips/nip-34/kind-1631/schema.json",
        "kind1632Schema" to "schemas/nips/nip-34/kind-1632/schema.json",
        "kind1633Schema" to "schemas/nips/nip-34/kind-1633/schema.json",
        "kind30617Schema" to "schemas/nips/nip-34/kind-30617/schema.json",
        "kind30618Schema" to "schemas/nips/nip-34/kind-30618/schema.json",
        "appliedascommitsTagSchema" to "schemas/nips/nip-34/tag/applied-as-commits/schema.json",
        "cloneTagSchema" to "schemas/nips/nip-34/tag/clone/schema.json",
        "commitTagSchema" to "schemas/nips/nip-34/tag/commit/schema.json",
        "commitpgpsigTagSchema" to "schemas/nips/nip-34/tag/commit-pgp-sig/schema.json",
        "committerTagSchema" to "schemas/nips/nip-34/tag/committer/schema.json",
        "erootTagSchema" to "schemas/nips/nip-34/tag/e-root/schema.json",
        "estatusreplyTagSchema" to "schemas/nips/nip-34/tag/e-status-reply/schema.json",
        "headTagSchema" to "schemas/nips/nip-34/tag/head/schema.json",
        "maintainersTagSchema" to "schemas/nips/nip-34/tag/maintainers/schema.json",
        "mergecommitTagSchema" to "schemas/nips/nip-34/tag/merge-commit/schema.json",
        "nameTagSchema" to "schemas/nips/nip-34/tag/name/schema.json",
        "parentcommitTagSchema" to "schemas/nips/nip-34/tag/parent-commit/schema.json",
        "reucTagSchema" to "schemas/nips/nip-34/tag/r-euc/schema.json",
        "refTagSchema" to "schemas/nips/nip-34/tag/ref/schema.json",
        "webTagSchema" to "schemas/nips/nip-34/tag/web/schema.json",
        // nip-36
        "contentwarningTagSchema" to "schemas/nips/nip-36/tag/content-warning/schema.json",
        // nip-38
        "kind30315Schema" to "schemas/nips/nip-38/kind-30315/schema.json",
        "expirationTagSchema" to "schemas/nips/nip-38/tag/expiration/schema.json",
        "statustypeTagSchema" to "schemas/nips/nip-38/tag/status-type/schema.json",
        // nip-40
        "clientAuthSchema" to "schemas/nips/nip-40/messages/client-auth/schema.json",
        "relayAuthSchema" to "schemas/nips/nip-40/messages/relay-auth/schema.json",
        // nip-42
        "kind22242Schema" to "schemas/nips/nip-42/kind-22242/schema.json",
        "challengeTagSchema" to "schemas/nips/nip-42/tag/challenge/schema.json",
        // nip-52
        "kind31922Schema" to "schemas/nips/nip-52/kind-31922/schema.json",
        "kind31923Schema" to "schemas/nips/nip-52/kind-31923/schema.json",
        "kind31924Schema" to "schemas/nips/nip-52/kind-31924/schema.json",
        "kind31925Schema" to "schemas/nips/nip-52/kind-31925/schema.json",
        "endtzidTagSchema" to "schemas/nips/nip-52/tag/end_tzid/schema.json",
        "fbTagSchema" to "schemas/nips/nip-52/tag/fb/schema.json",
        "gTagSchema" to "schemas/nips/nip-52/tag/g/schema.json",
        "locationTagSchema" to "schemas/nips/nip-52/tag/location/schema.json",
        "starttzidTagSchema" to "schemas/nips/nip-52/tag/start_tzid/schema.json",
        "statusTagSchema" to "schemas/nips/nip-52/tag/status/schema.json",
        // nip-53
        "kind10312Schema" to "schemas/nips/nip-53/kind-10312/schema.json",
        "kind1311Schema" to "schemas/nips/nip-53/kind-1311/schema.json",
        "kind30311Schema" to "schemas/nips/nip-53/kind-30311/schema.json",
        "kind30312Schema" to "schemas/nips/nip-53/kind-30312/schema.json",
        "kind30313Schema" to "schemas/nips/nip-53/kind-30313/schema.json",
        "aliveTagSchema" to "schemas/nips/nip-53/tag/a-live/schema.json",
        "aroomTagSchema" to "schemas/nips/nip-53/tag/a-room/schema.json",
        "currentparticipantsTagSchema" to "schemas/nips/nip-53/tag/current_participants/schema.json",
        "endpointTagSchema" to "schemas/nips/nip-53/tag/endpoint/schema.json",
        "endsTagSchema" to "schemas/nips/nip-53/tag/ends/schema.json",
        "handTagSchema" to "schemas/nips/nip-53/tag/hand/schema.json",
        "imageTagSchema" to "schemas/nips/nip-53/tag/image/schema.json",
        "pinnedTagSchema" to "schemas/nips/nip-53/tag/pinned/schema.json",
        "recordingTagSchema" to "schemas/nips/nip-53/tag/recording/schema.json",
        "relaysTagSchema" to "schemas/nips/nip-53/tag/relays/schema.json",
        "roomTagSchema" to "schemas/nips/nip-53/tag/room/schema.json",
        "serviceTagSchema" to "schemas/nips/nip-53/tag/service/schema.json",
        "startsTagSchema" to "schemas/nips/nip-53/tag/starts/schema.json",
        "statusliveTagSchema" to "schemas/nips/nip-53/tag/status-live/schema.json",
        "statusroomTagSchema" to "schemas/nips/nip-53/tag/status-room/schema.json",
        "streamingTagSchema" to "schemas/nips/nip-53/tag/streaming/schema.json",
        "summaryTagSchema" to "schemas/nips/nip-53/tag/summary/schema.json",
        "titleTagSchema" to "schemas/nips/nip-53/tag/title/schema.json",
        "totalparticipantsTagSchema" to "schemas/nips/nip-53/tag/total_participants/schema.json",
        // nip-56
        "kind1984Schema" to "schemas/nips/nip-56/kind-1984/schema.json",
        "serverTagSchema" to "schemas/nips/nip-56/tag/server/schema.json",
        // nip-57
        "kind9734Schema" to "schemas/nips/nip-57/kind-9734/schema.json",
        "kind9735Schema" to "schemas/nips/nip-57/kind-9735/schema.json",
        "amountTagSchema" to "schemas/nips/nip-57/tag/amount/schema.json",
        "bolt11TagSchema" to "schemas/nips/nip-57/tag/bolt11/schema.json",
        "descriptionTagSchema" to "schemas/nips/nip-57/tag/description/schema.json",
        "lnurlTagSchema" to "schemas/nips/nip-57/tag/lnurl/schema.json",
        "preimageTagSchema" to "schemas/nips/nip-57/tag/preimage/schema.json",
        // nip-59
        "kind1059Schema" to "schemas/nips/nip-59/kind-1059/schema.json",
        "kind13Schema" to "schemas/nips/nip-59/kind-13/schema.json",
        // nip-61
        "kind10019Schema" to "schemas/nips/nip-61/kind-10019/schema.json",
        "kind7376Schema" to "schemas/nips/nip-61/kind-7376/schema.json",
        "kind9321Schema" to "schemas/nips/nip-61/kind-9321/schema.json",
        "eredeemedTagSchema" to "schemas/nips/nip-61/tag/e-redeemed/schema.json",
        "mintTagSchema" to "schemas/nips/nip-61/tag/mint/schema.json",
        "proofTagSchema" to "schemas/nips/nip-61/tag/proof/schema.json",
        "pubkeyTagSchema" to "schemas/nips/nip-61/tag/pubkey/schema.json",
        "uTagSchema" to "schemas/nips/nip-61/tag/u/schema.json",
        // nip-65
        "kind10002Schema" to "schemas/nips/nip-65/kind-10002/schema.json",
        "rTagSchema" to "schemas/nips/nip-65/tag/r/schema.json",
        // nip-68
        "kind20Schema" to "schemas/nips/nip-68/kind-20/schema.json",
        "imetaTagSchema" to "schemas/nips/nip-68/tag/imeta/schema.json",
        // nip-73
        "iTagSchema" to "schemas/nips/nip-73/tag/i/schema.json",
        // nip-78
        "kind30078Schema" to "schemas/nips/nip-78/kind-30078/schema.json",
        // nip-84
        "kind9802Schema" to "schemas/nips/nip-84/kind-9802/schema.json",
        "commentTagSchema" to "schemas/nips/nip-84/tag/comment/schema.json",
        "contextTagSchema" to "schemas/nips/nip-84/tag/context/schema.json",
        // nip-88
        "kind1018Schema" to "schemas/nips/nip-88/kind-1018/schema.json",
        "kind1068Schema" to "schemas/nips/nip-88/kind-1068/schema.json",
        "endsAtTagSchema" to "schemas/nips/nip-88/tag/endsAt/schema.json",
        "optionTagSchema" to "schemas/nips/nip-88/tag/option/schema.json",
        "polltypeTagSchema" to "schemas/nips/nip-88/tag/polltype/schema.json",
        "responseTagSchema" to "schemas/nips/nip-88/tag/response/schema.json",
        // nip-89
        "kind31989Schema" to "schemas/nips/nip-89/kind-31989/schema.json",
        "kind31990Schema" to "schemas/nips/nip-89/kind-31990/schema.json",
        "clientTagSchema" to "schemas/nips/nip-89/tag/client/schema.json",
        // nip-99
        "kind30402Schema" to "schemas/nips/nip-99/kind-30402/schema.json",
        "kind30403Schema" to "schemas/nips/nip-99/kind-30403/schema.json",
        // nip-b7
        "kind10063Schema" to "schemas/nips/nip-b7/kind-10063/schema.json",
        // mip-00
        "kind10051Schema" to "schemas/mips/mip-00/kind-10051/schema.json",
        "kind443Schema" to "schemas/mips/mip-00/kind-443/schema.json",
        "encodingTagSchema" to "schemas/mips/mip-00/tag/encoding/schema.json",
        "mlsciphersuiteTagSchema" to "schemas/mips/mip-00/tag/mls_ciphersuite/schema.json",
        "mlsextensionsTagSchema" to "schemas/mips/mip-00/tag/mls_extensions/schema.json",
        "mlsprotocolversionTagSchema" to "schemas/mips/mip-00/tag/mls_protocol_version/schema.json",
        // mip-02
        "kind444Schema" to "schemas/mips/mip-02/kind-444/schema.json",
        // mip-03
        "kind445Schema" to "schemas/mips/mip-03/kind-445/schema.json",
        "hTagSchema" to "schemas/mips/mip-03/tag/h/schema.json",
        // @ (cross-cutting)
        "messagefilterSchema" to "schemas/@/message/filter.json",
        "nip05Schema" to "schemas/@/nip05.json",
        "nostrwellknownSchema" to "schemas/@/nostr-well-known.json",
        "notekind22242Schema" to "schemas/@/note/kind-22242.json",
        "noteunsignedSchema" to "schemas/@/note-unsigned.json",
        "authrelayTagSchema" to "schemas/@/tag/auth-relay.json",
        "dmrelayTagSchema" to "schemas/@/tag/dm-relay.json",
        "externalcontentiTagSchema" to "schemas/@/tag/external-content-i.json",
        "externalcontentkTagSchema" to "schemas/@/tag/external-content-k.json",
        "gitrefTagSchema" to "schemas/@/tag/git-ref.json",
        "mip00iTagSchema" to "schemas/@/tag/mip-00_i.json",
        "mlsclientTagSchema" to "schemas/@/tag/mls_client.json",
        "reactioneTagSchema" to "schemas/@/tag/reaction-e.json",
    )

    /** Cache of parsed schemas, populated on first access per key. */
    private val cache: MutableMap<String, JsonElement> = mutableMapOf()

    /**
     * Returns the parsed JSON schema for the given registry key, or null if not found.
     * Schemas are loaded lazily and cached after first access.
     */
    fun get(key: String): JsonElement? {
        cache[key]?.let { return it }

        val resourcePath = registry[key] ?: return null

        val text = Schemata::class.java.classLoader
            ?.getResourceAsStream(resourcePath)
            ?.bufferedReader()
            ?.readText()
            ?: return null

        val parsed = json.parseToJsonElement(text)
        cache[key] = parsed
        return parsed
    }

    /**
     * Returns the set of all available schema keys.
     */
    fun keys(): Set<String> = registry.keys
}
