package b

fun main() {
    val result = top3("qVFU' qVFU'!fg'iy AGloqhKZ'I NmXp vGtzndkL qVFU' ikoLIwXI SyeWJbPh CADH fg'iy AGloqhKZ'I ikoLIwXI pbUhaGStj,pbUhaGStj:oTDAW pbUhaGStj-ikoLIwXI-ikoLIwXI:vGtzndkL ILDnWj:fg'iy/ILDnWj!fg'iy pbUhaGStj TybKMvvbFt aLXpogRYiN,qVFU',TybKMvvbFt SyeWJbPh CADH qVFU':AGloqhKZ'I TybKMvvbFt bBdeH?oTDAW NmXp.oTDAW pbUhaGStj ikoLIwXI:aLXpogRYiN fg'iy TybKMvvbFt:AGloqhKZ'I-AGloqhKZ'I.aLXpogRYiN NmXp-oTDAW:fg'iy CADH/CADH ikoLIwXI AGloqhKZ'I:CADH!CADH qVFU';oTDAW_TybKMvvbFt-qVFU'.TybKMvvbFt qVFU'/bBdeH!aLXpogRYiN?oTDAW:oTDAW oTDAW CADH-CADH qVFU'/NmXp aLXpogRYiN!DVFeQZyENd TybKMvvbFt ikoLIwXI CADH pbUhaGStj qVFU'/pbUhaGStj DVFeQZyENd SyeWJbPh-ikoLIwXI TybKMvvbFt.pbUhaGStj bBdeH NmXp_aLXpogRYiN/ikoLIwXI.qVFU' aLXpogRYiN fg'iy qVFU' pbUhaGStj DVFeQZyENd;pbUhaGStj oTDAW!AGloqhKZ'I/pbUhaGStj_CADH,pbUhaGStj?TybKMvvbFt.AGloqhKZ'I:AGloqhKZ'I DVFeQZyENd?SyeWJbPh SyeWJbPh;vGtzndkL:ikoLIwXI:pbUhaGStj DVFeQZyENd.CADH CADH?oTDAW DVFeQZyENd NmXp vGtzndkL-TybKMvvbFt-fg'iy ikoLIwXI-ILDnWj qVFU' AGloqhKZ'I/fg'iy pbUhaGStj/qVFU'?ikoLIwXI_AGloqhKZ'I vGtzndkL,ILDnWj oTDAW,SyeWJbPh oTDAW aLXpogRYiN.ILDnWj/ikoLIwXI SyeWJbPh qVFU';OseSygjm.TybKMvvbFt:fg'iy qVFU'/SyeWJbPh NmXp oTDAW pbUhaGStj TybKMvvbFt oTDAW;oTDAW pbUhaGStj!pbUhaGStj ikoLIwXI/pbUhaGStj qVFU'-NmXp_TybKMvvbFt oTDAW DVFeQZyENd pbUhaGStj?ILDnWj fg'iy_pbUhaGStj SyeWJbPh_DVFeQZyENd NmXp ikoLIwXI ikoLIwXI/qVFU'?CADH/qVFU'_qVFU' qVFU' SyeWJbPh ILDnWj?SyeWJbPh DVFeQZyENd-ILDnWj,NmXp qVFU'/qVFU' qVFU' SyeWJbPh_oTDAW pbUhaGStj,SyeWJbPh,CADH TybKMvvbFt AGloqhKZ'I NmXp:oTDAW/AGloqhKZ'I AGloqhKZ'I SyeWJbPh;KsIL qVFU' SyeWJbPh!ikoLIwXI/ikoLIwXI:NmXp oTDAW CADH_AGloqhKZ'I;SyeWJbPh NmXp ikoLIwXI,qVFU':CADH/DVFeQZyENd;SyeWJbPh aLXpogRYiN pbUhaGStj-SyeWJbPh.AGloqhKZ'I oTDAW;oTDAW pbUhaGStj,pbUhaGStj aLXpogRYiN.oTDAW fg'iy:oTDAW/NmXp;oTDAW/oTDAW/NmXp!AGloqhKZ'I qVFU' qVFU' SyeWJbPh DVFeQZyENd OseSygjm?aLXpogRYiN,oTDAW;fg'iy.DVFeQZyENd qVFU' pbUhaGStj?TybKMvvbFt AGloqhKZ'I TybKMvvbFt oTDAW;TybKMvvbFt?SyeWJbPh TybKMvvbFt:SyeWJbPh DVFeQZyENd pbUhaGStj_TybKMvvbFt-TybKMvvbFt:fg'iy!DVFeQZyENd;NmXp ILDnWj_aLXpogRYiN oTDAW AGloqhKZ'I qVFU'/ikoLIwXI,pbUhaGStj.aLXpogRYiN vGtzndkL.DVFeQZyENd.fg'iy aLXpogRYiN AGloqhKZ'I ikoLIwXI AGloqhKZ'I-aLXpogRYiN?SyeWJbPh.oTDAW,aLXpogRYiN ikoLIwXI?AGloqhKZ'I?pbUhaGStj ")
    val __dbg = Any()
}

val wordRegex: Regex = Regex("[a-z']+", RegexOption.IGNORE_CASE)
val invalidWordRegex: Regex = Regex("^'+$")

fun top3(s: String): List<String> {
    return wordRegex.findAll(s)
        .map { it.value.lowercase() }
        .filterNot { it matches invalidWordRegex }
        .groupingBy { it }
        .eachCount()
        .asSequence()
        .sortedByDescending { it.value }
        .take(3)
        .map { it.key }
        .toList()
}